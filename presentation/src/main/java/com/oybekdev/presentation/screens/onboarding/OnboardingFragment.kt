package com.oybekdev.presentation.screens.onboarding

import android.os.Bundle
import android.view.View
import com.oybekdev.presentation.base.BaseFragment
import com.oybekdev.presentation.databinding.FragmentOnboardingBinding
import com.oybekdev.presentation.screens.onboarding.OnboardingViewModel.Input
import org.koin.androidx.viewmodel.ext.android.viewModel


class OnboardingFragment:BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {

    private val viewModel: OnboardingViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() = with(binding) {
        next.setOnClickListener {
            viewModel.processInput(Input.Onboarded)
        }
    }
}