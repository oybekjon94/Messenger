package com.oybekdev.domain.usecase.chat

import com.oybekdev.domain.repo.ChatRepository

class GetChatsUseCase(
    private val charRepository: ChatRepository,
) {
    operator fun invoke() = charRepository.getChats()
}