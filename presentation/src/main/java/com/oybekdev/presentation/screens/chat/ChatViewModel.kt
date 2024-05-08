package com.oybekdev.presentation.screens.chat

import android.net.Uri
import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.model.Type
import com.oybekdev.domain.usecase.chat.GetMessageUseCase
import com.oybekdev.domain.usecase.chat.SendImageUseCase
import com.oybekdev.domain.usecase.chat.SendMessageUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.screens.chat.ChatViewModel.*
import java.io.InputStream
import java.util.Date

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase:GetMessageUseCase,
    private val sendImageUseCase: SendImageUseCase
) :BaseViewModel<State, Input, Effect>(){

    data class State(
        val messages:List<Message> = emptyList(),
        val loading:Boolean = false,
        val error:Boolean = false,
        val chat:Chat? = null
    )

    sealed class Input{
        object GetMessages:Input()
        data class SendMessage(val message:String):Input()
        data class SendImage(val image: Uri, val stream: InputStream):Input()
        data class SetChat(val chat: Chat):Input()
    }

    sealed class Effect{
        object ErrorSending:Effect()
    }

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when(input){
            Input.GetMessages -> getMessages()
            is Input.SendMessage -> sendMessage(input.message)
            is Input.SetChat -> setChat(input.chat)
            is Input.SendImage -> sendImage(input.image,input.stream)
        }
    }

    private fun sendImage(image: Uri, stream: InputStream) {
        val message = Message(id = image.toString(), time = Date(), type = Type.image_upload, image = image)
        val messages = current.messages.toMutableList()
        messages.add(message)
        updateState { it.copy(messages = messages) }

        sendImageUseCase(current.chat!!.user.id, stream)
            .doOnError{
                emitEffect(Effect.ErrorSending)
            }.subscribe({},{})
    }

    private fun setChat(chat: Chat) {
        updateState { it.copy(chat = chat) }
        getMessages()
    }

    private fun sendMessage(message: String) = sendMessageUseCase(current.chat!!.user.id, message)
        .doOnError{
            emitEffect(Effect.ErrorSending)
        }.subscribe({},{})

    private fun getMessages() = getMessageUseCase(current.chat!!.user.id)
        .doOnSubscribe{
            updateState { it.copy(loading = true) }
        }.doOnEach{
            updateState { it.copy(loading = false) }
        }.doOnError{
            emitEffect(Effect.ErrorSending)
        }.doOnNext{messages ->
            updateState { it.copy(messages = messages) }
        }.subscribe()
}