package com.oybekdev.presentation.screens.phone

import com.github.terrakok.cicerone.Router
import com.oybekdev.domain.model.User
import com.oybekdev.domain.usecase.auth.SendSmsCodeUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.navigation.Screens.CodeScreen
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Effect
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Input
import com.oybekdev.presentation.screens.phone.PhoneViewModel.State

class PhoneViewModel constructor(
    private val sendSmsCodeUseCase: SendSmsCodeUseCase,
    private val router: Router,
) : BaseViewModel<State, Input, Effect>() {

    data class State(
        val loading: Boolean = false,
    )

    sealed class Effect {
        object Error : Effect()
    }

    sealed class Input {
        data class SendCode(val phone: String) : Input()
    }

    override fun getDefaultState() = State()
    override fun processInput(input: Input) {
        when (input) {
            is Input.SendCode -> sendCode(input.phone)
        }
    }

    private fun sendCode(phone: String) = sendSmsCodeUseCase(phone)
            .doOnSubscribe {
                updateState { it.copy(loading = true) }
            }.doOnError {
                emitEffect(Effect.Error)
            }.doOnComplete {
                router.navigateTo(CodeScreen(phone))
            }.doFinally {
                updateState { it.copy(loading = false) }
            }.subscribe({},{})

}