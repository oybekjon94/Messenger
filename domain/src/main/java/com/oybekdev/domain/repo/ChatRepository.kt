package com.oybekdev.domain.repo

import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.model.User
import io.reactivex.rxjava3.core.Single

interface ChatRepository {
    fun getChats(): Single<List<Chat>>
}