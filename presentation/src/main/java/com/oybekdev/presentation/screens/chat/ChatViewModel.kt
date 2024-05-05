package com.oybekdev.presentation.screens.chat

import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.usecase.chat.GetMessageUseCase
import com.oybekdev.domain.usecase.chat.SendMessageUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.screens.chat.ChatViewModel.*

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase:GetMessageUseCase
) :BaseViewModel<State, Input, Effect>(){

//    init {
//        getMessages()
//    }

    data class State(
        val messages:List<Message> = emptyList(),
        val loading:Boolean = false,
        val error:Boolean = false,
        val chat:Chat? = null
    )

    sealed class Input{
        object GetMessages:Input()
        data class SendMessage(val message:String):Input()
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
        }
    }

    private fun setChat(chat: Chat) {
        updateState { it.copy(chat = chat) }
        getMessages()
    }

    private fun sendMessage(message: String) = sendMessageUseCase(current.chat!!.user.id, message)
        .doOnError{
            emitEffect(Effect.ErrorSending)
        }.subscribe()

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