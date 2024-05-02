package com.oybekdev.domain.repo

interface AuthRepository {
    fun sendSmsCode(phone:String)
}