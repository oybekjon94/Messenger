package com.oybekdev.data.repo

import com.oybekdev.data.mapper.toMessage
import com.oybekdev.data.mapper.toUser
import com.oybekdev.data.remote.auth.AuthFirebase
import com.oybekdev.data.remote.files.ImagesStorage
import com.oybekdev.data.remote.messages.MessagesFirestore
import com.oybekdev.data.remote.push.PushVolley
import com.oybekdev.data.remote.users.UsersFirestore
import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.model.User
import com.oybekdev.domain.repo.ChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream

class CharRepositoryImpl(
    private val usersFirestore: UsersFirestore,
    private val messagesFirestore: MessagesFirestore,
    private val authFirebase: AuthFirebase,
    private val imagesStorage: ImagesStorage,
    private val pushVolley:PushVolley
) : ChatRepository {
    override fun getChats(): Single<List<Chat>> = usersFirestore.getUsers().map { users ->
        users.mapNotNull { user ->
            user.toUser()?.let { Chat(user = it) }
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessage(to: User, message: String): Completable =
        messagesFirestore.sendMessaage(authFirebase.userId!!, to.id, message).andThen(
            pushVolley.push(to.token, "New message",message)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun sendMessage(to: User, image: InputStream): Completable =
        imagesStorage.upload(image).flatMapCompletable {
            messagesFirestore.sendMessaage(authFirebase.userId!!, to.id, it)
        }.andThen(pushVolley.push(to.token, "New message","[Image]"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getMessages(with: String): Observable<List<Message>> =
        messagesFirestore.getMessages(authFirebase.userId!!, with).map { messages ->
            messages.mapNotNull { it.toMessage(authFirebase.userId!!) }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}