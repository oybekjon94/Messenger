package com.oybekdev.domain.usecase.chat

import com.oybekdev.domain.repo.ChatRepository

class GetMessageUseCase(
    private val charRepository: ChatRepository
) {
    operator fun invoke(with:String) = charRepository.getMessages(with)
    
}