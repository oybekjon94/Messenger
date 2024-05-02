package com.oybekdev.presentation.screens.main

import com.github.terrakok.cicerone.Router
import com.oybekdev.presentation.base.BaseViewModel
import com.oybekdev.presentation.navigation.Screens.Phone
import com.oybekdev.presentation.screens.main.MainViewModel.*

class MainViewModel(
    private val router: Router
) :BaseViewModel<State, Input,Effect>(){

    class State

    class Input

    class Effect

    init {
        router.newRootScreen(Phone())
    }

    override fun getDefaultState() = State()

    override fun processInput(input: Input) {

    }


}