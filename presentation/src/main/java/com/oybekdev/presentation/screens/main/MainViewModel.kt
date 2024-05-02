package com.oybekdev.presentation.screens.main

import android.annotation.SuppressLint
import com.github.terrakok.cicerone.Router
import com.oybekdev.domain.usecase.settings.GetOnboardedUseCase
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.navigation.Screens.OnboardingScreen
import com.oybekdev.presentation.navigation.Screens.PhoneScreen
import com.oybekdev.presentation.screens.main.MainViewModel.*

class MainViewModel(
    private val getOnboardedUseCase: GetOnboardedUseCase,
    private val router: Router
) :BaseViewModel<State, Input,Effect>(){

    class State

    class Input

    class Effect

    init {
        getOnboarded()
    }

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {

    }

    @SuppressLint("CheckResult")
    private fun getOnboarded(){
        getOnboardedUseCase().subscribe{ onboarded ->
            router.newRootScreen(
                if (onboarded) PhoneScreen() else OnboardingScreen()
            )
        }
    }
}