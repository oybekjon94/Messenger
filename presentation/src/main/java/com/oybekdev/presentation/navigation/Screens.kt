package com.oybekdev.presentation.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.oybekdev.domain.model.Chat
import com.oybekdev.presentation.screens.chat.ChatFragment
import com.oybekdev.presentation.screens.code.CodeFragment
import com.oybekdev.presentation.screens.home.HomeFragment
import com.oybekdev.presentation.screens.onboarding.OnboardingFragment
import com.oybekdev.presentation.screens.phone.PhoneFragment

object Screens {
    fun PhoneScreen() = FragmentScreen{PhoneFragment()}
    fun OnboardingScreen() = FragmentScreen{ OnboardingFragment() }
    fun CodeScreen(phone:String) = FragmentScreen{ CodeFragment(phone) }
    fun HomeScreen() = FragmentScreen{ HomeFragment() }
    fun ChatScreen(chat: Chat) = FragmentScreen(){ChatFragment(chat)}
}