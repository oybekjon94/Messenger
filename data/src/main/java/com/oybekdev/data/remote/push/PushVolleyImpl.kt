package com.oybekdev.data.remote.push

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import io.reactivex.rxjava3.core.Completable
import org.json.JSONObject

class PushVolleyImpl(
    private val queue: RequestQueue,
) : PushVolley {
    override fun push(token: String?, title: String, body: String): Completable =
        Completable.create { emit ->

            token ?: kotlin.run{
                emit.onComplete()
                return@create
            }

            val url = "https://fcm.googleapis.com/fcm/send"

            val notification = JSONObject()
            notification.put("title", title)
            notification.put("body", body)

            val message = JSONObject()
            message.put("to", token)
            message.put("notification", notification)

            val request = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                message,
                {
                emit.onComplete()
                },
                { error ->
                    emit.onError(error)
                }
            ){
                override fun getHeaders(): MutableMap<String, String> {
                    return mutableMapOf(
                        "Content-Type" to "application/json",
                        "Authorization" to "key=BN2eMskaFeNkNjzG12fyECiHESkvzhfncCu8JTbuISfv634RDI1NGcD3vmP0bLASa1sJUt6N-xeSqk2ZM9IwXoE"
                    )
                }
            }
            queue.add(request)
        }

}