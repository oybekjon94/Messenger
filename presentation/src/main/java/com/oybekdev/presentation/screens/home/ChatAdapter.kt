package com.oybekdev.presentation.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oybekdev.domain.model.Chat
import com.oybekdev.presentation.R
import com.oybekdev.presentation.databinding.ItemChatBinding
import kotlin.reflect.KFunction1

class ChatAdapter(private val chats:List<Chat>, private val onClick: KFunction1<Chat, Unit>):RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding:ItemChatBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(chat:Chat) = with(binding){
            Glide.with(root).load(R.drawable.ic_person).into(avatar)
            name.text = chat.user.name
            last.text = chat.user.phone
            root.setOnClickListener {
                onClick(chat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chats[position])
    }
}