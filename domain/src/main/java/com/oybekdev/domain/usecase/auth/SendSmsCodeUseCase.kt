package com.oybekdev.domain.usecase.auth

import com.oybekdev.domain.repo.AuthRepository

class SendSmsCodeUseCase constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(phone:String){
        authRepository.sendSmsCode(phone)
    }
}