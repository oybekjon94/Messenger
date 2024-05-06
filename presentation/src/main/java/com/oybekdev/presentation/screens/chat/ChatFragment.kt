package com.oybekdev.presentation.screens.chat

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.oybekdev.domain.model.Chat
import com.oybekdev.domain.model.Message
import com.oybekdev.presentation.base.BaseFragment
import com.oybekdev.presentation.databinding.FragmentChatBinding
import com.oybekdev.presentation.screens.chat.ChatViewModel.Input
import com.oybekdev.presentation.screens.chat.ChatViewModel.Input.SendMessage
import org.koin.androidx.viewmodel.ext.android.viewModel



class ChatFragment(
    private val chat: Chat
) :BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate){
    private val viewModel:ChatViewModel by viewModel()
    private val adapter = ChatAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.processInput(Input.SetChat(chat))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        viewModel.state.observe(::renderMessage){it.messages}
    }

    private fun renderMessage(messages:List<Message>){
        adapter.submitList(messages)
    }

    private fun initUi() = with(binding) {
       send.setOnClickListener{
           viewModel.processInput(SendMessage(message.text.toString()))
           message.text = null
       }

        gallery.setOnClickListener{
            galleryLauncher.launch("image/*")
        }
        messages.adapter = adapter
    }
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        it ?: return@registerForActivityResult
        viewModel.processInput(Input.SendImage(it))
    }
}