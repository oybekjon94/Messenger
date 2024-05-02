package com.oybekdev.data.repo

import com.oybekdev.data.remote.auth.AuthFirebase
import com.oybekdev.domain.repo.AuthRepository

class AuthRepositoryImpl constructor(
    private val authFirebase: AuthFirebase
):AuthRepository {
    override fun sendSmsCode(phone: String) {
        authFirebase.sendSmsCode(phone)
    }
}