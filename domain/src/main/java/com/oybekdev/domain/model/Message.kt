package com.oybekdev.domain.model

import android.net.Uri
import java.util.Date

data class Message(
    var id:String,
    var message:String? = null,
    var time: Date,
    val type:Type,
    val imageUri: Uri? = null
)
enum class Type{
    text_in, text_out, image_upload
}