package com.oybekdev.data.remote.auth

interface AuthFirebase {
    fun sendSmsCode(phone:String)
}