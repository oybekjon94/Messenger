package com.oybekdev.data.repo

import com.oybekdev.data.mapper.toUser
import com.oybekdev.data.remote.users.UsersFirestore
import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.repo.ChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CharRepositoryImpl(
    private val usersFirestore: UsersFirestore
):ChatRepository {
    override fun getChats(): Single<List<Chat>> = usersFirestore.getUsers().map{users ->
        users.mapNotNull {user ->
            user.toUser()?.let { Chat(user = it) }
        }
    }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}