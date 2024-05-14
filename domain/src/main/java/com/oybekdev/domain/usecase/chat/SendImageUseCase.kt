package com.oybekdev.domain.usecase.chat

import com.oybekdev.domain.model.User
import com.oybekdev.domain.repo.ChatRepository
import java.io.InputStream

class SendImageUseCase(
    private val charRepository: ChatRepository
) {
    operator fun invoke(to: User, image:InputStream) = charRepository.sendMessage(to, image)
}