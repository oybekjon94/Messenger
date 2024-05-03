package com.oybekdev.domain.usecase.chat

import com.oybekdev.domain.repo.ChatRepository

class SendMessageUseCase(
    private val charRepository: ChatRepository
) {
    operator fun invoke(to:String, message:String) = charRepository.sendMessage(to, message)

}