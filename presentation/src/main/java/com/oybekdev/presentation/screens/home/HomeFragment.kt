package com.oybekdev.presentation.screens.home

import android.os.Bundle
import android.view.View
import com.oybekdev.domain.model.Chat
import com.oybekdev.presentation.base.BaseFragment
import com.oybekdev.presentation.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment:BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(::renderChats){it.chats}
        viewModel.state.observe(::renderLoading){it.loading}
        viewModel.state.observe(::renderError){it.error}
    }

    private fun renderError(error:Boolean){

    }
    private fun renderLoading(loading:Boolean){

    }
    private fun renderChats(chats:List<Chat>) = with(binding){
        binding.chats.adapter = ChatAdapter(chats)
    }
}