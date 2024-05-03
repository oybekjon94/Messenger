package com.oybekdev.presentation.screens.phone

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.oybekdev.domain.model.User
import com.oybekdev.presentation.R
import com.oybekdev.presentation.base.BaseFragment
import com.oybekdev.presentation.databinding.FragmentPhoneBinding
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Effect
import com.oybekdev.presentation.screens.phone.PhoneViewModel.Input
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhoneFragment:BaseFragment<FragmentPhoneBinding>(FragmentPhoneBinding::inflate) {

    private val viewModel: PhoneViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        viewModel.effects.subscribe(::handleEffects)
        viewModel.state.observe(::renderLoading){it.loading}
    }
    private fun renderLoading(isLoading:Boolean) = with(binding){
        progress.isVisible = isLoading
        signIn.text = getText(R.string.fragment_phone_sign_in).takeIf { isLoading.not() }
    }

    private fun initUi() = with(binding) {
        phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        signIn.setOnClickListener {
            viewModel.processInput(Input.SendCode(phone.text.toString()))
        }
    }

    private fun handleEffects(effect: Effect){
        when(effect){
            Effect.Error -> snackbar(R.string.phone_error)
        }
    }

}