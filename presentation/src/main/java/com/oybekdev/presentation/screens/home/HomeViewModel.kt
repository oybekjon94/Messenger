package com.oybekdev.presentation.screens.home

import com.github.terrakok.cicerone.Router
import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.usecase.chat.GetChatsUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.navigation.Screens.ChatScreen
import com.oybekdev.presentation.screens.home.HomeViewModel.*

class HomeViewModel(
    private val getChatsUseCase: GetChatsUseCase,
    private val router: Router
) :BaseViewModel<State, Input, Effect>(){

    init {
        getChats()
    }

    data class State(
        val chats:List<Chat> = emptyList(),
        val loading:Boolean = false,
        val error:Boolean = false
    )

    sealed class Input{
        object GetChats:Input()
        data class Open(val chat:Chat):Input()
    }

    class Effect

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when(input){
            Input.GetChats -> getChats()
            is Input.Open -> open(input.chat)
        }
    }

    private fun open(chat: Chat) {
        router.navigateTo(ChatScreen(chat))
    }

    private fun getChats() = getChatsUseCase()
        .doOnSubscribe{
            updateState { it.copy(loading = true, error = false) }
        }
        .doOnError{
            updateState { it.copy( error = true) }
        }
        .doAfterSuccess{chats ->
            updateState { it.copy(chats = chats) }
        }
        .doFinally{
            updateState { it.copy(loading = false) }
        }.subscribe({},{})
}