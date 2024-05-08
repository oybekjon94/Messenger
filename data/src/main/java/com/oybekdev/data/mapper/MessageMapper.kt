package com.oybekdev.data.mapper

import android.net.Uri
import com.oybekdev.data.remote.messages.MessageDocument
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.model.Type

fun MessageDocument.toMessage(userId:String):Message?{
    return Message(
        id = id ?: return null,
        message = message,
        time = time ?: return null,
        type = when(from){
            userId -> when{
                image != null -> Type.image_out
                else -> Type.text_out
            }
            else -> when{
                image != null -> Type.image_in
                else -> Type.text_in
            }
        },
        image = image?.let { Uri.parse(it) }
    )
}