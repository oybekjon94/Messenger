package com.oybekdev.presentation.screens.code

import android.os.Bundle
import android.view.View
import com.oybekdev.presentation.R
import com.oybekdev.presentation.base.BaseFragment
import com.oybekdev.presentation.databinding.FragmentCodeBinding
import com.oybekdev.presentation.screens.code.CodeViewModel.Effect
import com.oybekdev.presentation.screens.code.CodeViewModel.Input
import com.oybekdev.presentation.screens.phone.PhoneViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CodeFragment(
    private val phone: String,
) : BaseFragment<FragmentCodeBinding>(FragmentCodeBinding::inflate) {

    private val viewModel: CodeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        viewModel.effects.subscribe { handleEffect(it) }
    }

    private fun handleEffect(effect: Effect) {
        when(effect){
            Effect.Error -> snackbar(R.string.code_error)
        }
    }

    private fun initUI() = with(binding) {

        verify.setOnClickListener {
            val otp = otp.otp?.takeIf { it.length == 6 } ?: return@setOnClickListener
            viewModel.processInput(Input.Verify(otp))
        }
        title.text = getString(R.string.fragment_code_sent_to, phone)
    }
}