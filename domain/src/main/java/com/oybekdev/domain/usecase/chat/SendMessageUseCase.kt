package com.oybekdev.domain.usecase.chat

import com.oybekdev.domain.model.User
import com.oybekdev.domain.repo.ChatRepository

class SendMessageUseCase(
    private val charRepository: ChatRepository
) {
    operator fun invoke(to: User, message:String) = charRepository.sendMessage(to, message)

}