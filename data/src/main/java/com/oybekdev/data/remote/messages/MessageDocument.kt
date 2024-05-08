package com.oybekdev.data.remote.messages

import android.net.Uri
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class MessageDocument(
    var id:String? = null,
    var message:String? = null,
    @ServerTimestamp
    var time: Date? = null,
    var members:String? = null,
    var count:Int? = null,
    val from:String? = null,
    var image: String? = null
)
