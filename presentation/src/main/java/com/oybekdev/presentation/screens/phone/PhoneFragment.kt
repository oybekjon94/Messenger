package com.oybekdev.presentation.screens.phone

import android.os.Bundle
import android.view.View
import com.oybekdev.domain.model.User
import com.oybekdev.presentation.base.BaseFragment
import com.oybekdev.presentation.databinding.FragmentPhoneBinding
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Effect
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneFragment:BaseFragment<FragmentPhoneBinding>(FragmentPhoneBinding::inflate) {

    private val viewModel: PhoneViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(::renderUser) { it.user!! }
        viewModel.effects.doOnNext(::handleEffects)
    }

    private fun renderUser(user: User?){

    }
    private fun handleEffects(effect: Effect){

    }

}