package com.oybekdev.domain.usecase.auth

import com.oybekdev.domain.repo.AuthRepository

class VerifyCodeUseCase constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(phone:String) = authRepository.verify(phone)
}