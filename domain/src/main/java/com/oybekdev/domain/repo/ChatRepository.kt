package com.oybekdev.domain.repo

import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.InputStream

interface ChatRepository {
    fun getChats(): Single<List<Chat>>
    fun sendMessage(to:User, message:String):Completable
    fun sendMessage(to:User, image:InputStream):Completable
    fun getMessages(with:String):Observable<List<Message>>
}