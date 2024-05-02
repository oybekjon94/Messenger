package com.oybekdev.presentation.screens.phone

import com.oybekdev.domain.model.User
import com.oybekdev.domain.usecase.auth.SendSmsCodeUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Effect
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Input
import com.oybekdev.presentation.screens.phone.PhoneViewModel.State

class PhoneViewModel constructor(
    private val sendSmsCodeUseCase: SendSmsCodeUseCase
) :BaseViewModel<State, Input ,Effect>(){

    data class State(
        val user: User? = null
    )

    class Effect

    class Input

    override fun getDefaultState()= State()

    override fun processInput(input: Input) {

    }

}