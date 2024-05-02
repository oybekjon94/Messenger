package com.oybekdev.presentation.screens.onboarding

import com.github.terrakok.cicerone.Router
import com.oybekdev.domain.usecase.settings.OnboardedUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.navigation.Screens.PhoneScreen
import com.oybekdev.presentation.screens.onboarding.OnboardingViewModel.*

class OnboardingViewModel(
    private val onboardedUseCase: OnboardedUseCase,
    private val router: Router
):BaseViewModel<State, Input, Effect>() {

    class State

    sealed class Input {
       object Onboarded: Input()
    }

    class Effect

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {
        when(input){
            Input.Onboarded -> onboarded()
        }
    }

    private fun onboarded() {
        onboardedUseCase().subscribe {
            router.replaceScreen(PhoneScreen())
        }
    }


}