package com.oybekdev.data.remote.messages

import android.net.Uri
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import java.net.URL

interface MessagesFirestore {
    fun sendMessaage(fromUserId:String, toUserId:String, message:String): Completable
    fun sendMessaage(fromUserId:String, toUserId:String, image:Uri): Completable
    fun getMessages(firstUserId:String, secondUserId:String): Observable<List<MessageDocument>>


}