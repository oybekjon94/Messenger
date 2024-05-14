package com.oybekdev.data.mapper

import com.oybekdev.data.remote.users.UserDocument
import com.oybekdev.domain.model.User

fun UserDocument.toUser():User?{
    return User(
        id = id?:return null,
        phone = phone?:return null,
        name = name?:return null,
        avatar = avatar,
        token = token
    )
}