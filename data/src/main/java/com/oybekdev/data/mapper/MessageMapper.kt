package com.oybekdev.data.mapper

import com.oybekdev.data.remote.messages.MessageDocument
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.model.Type

fun MessageDocument.toMessage(userId:String):Message?{
    return Message(
        id = id ?: return null,
        message = message ?: return null,
        time = time ?: return null,
        type = if(from == userId) Type.text_out else Type.text_in
    )
}