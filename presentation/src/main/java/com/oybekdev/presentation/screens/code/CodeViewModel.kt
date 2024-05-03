package com.oybekdev.presentation.screens.code

import com.github.terrakok.cicerone.Router
import com.oybekdev.domain.usecase.auth.VerifyCodeUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.navigation.Screens.HomeScreen
import com.oybekdev.presentation.screens.code.CodeViewModel.*

class CodeViewModel(
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val router: Router
) :BaseViewModel<State, Input, Effect>(){

    class State

    sealed class Input{
        data class Verify(val code:String):Input()
    }

    sealed class Effect{
        object Error:Effect()
    }

    override fun getDefaultState()= State()

    override fun processInput(input: Input) {
        when(input){
            is Input.Verify -> verify(input.code)
        }
    }

    private fun verify(code: String) = verifyCodeUseCase(code)
            .doOnError{
                emitEffect(Effect.Error)
            }.doOnComplete{
                router.replaceScreen(HomeScreen())
            }.subscribe()
}