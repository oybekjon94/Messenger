package com.oybekdev.presentation.screens.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oybekdev.domain.model.Message
import com.oybekdev.domain.model.Type
import com.oybekdev.presentation.databinding.ItemImageInChatBinding
import com.oybekdev.presentation.databinding.ItemImageOutChatBinding
import com.oybekdev.presentation.databinding.ItemImageUploadChatBinding
import com.oybekdev.presentation.databinding.ItemTextInChatBinding
import com.oybekdev.presentation.databinding.ItemTextOutChatBinding

class ChatAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(DIFF_UTIL) {

    inner class TextInViewHolder(private val binding: ItemTextInChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) = with(binding) {
            root.text = message.message
        }
    }

    inner class TextOutViewHolder(private val binding: ItemTextOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) = with(binding) {
            root.text = message.message
        }
    }

    inner class ImageUploadHolder(private val binding: ItemImageUploadChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(message:Message) = with(binding){
                Glide.with(root).load(message.image).into(image)
            }
    }
    inner class ImageInHolder(private val binding: ItemImageInChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(message:Message) = with(binding){
                Glide.with(root).load(message.image).into(image)
            }
    }
    inner class ImageOutHolder(private val binding: ItemImageOutChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(message:Message) = with(binding){
                Glide.with(root).load(message.image).into(image)
            }
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.message == newItem.message
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (Type.values()[viewType]) {
            Type.text_in -> TextInViewHolder(
                ItemTextInChatBinding.inflate(inflater, parent, false)
            )

            Type.text_out -> TextOutViewHolder(
                ItemTextOutChatBinding.inflate(inflater, parent, false)
            )

            Type.image_upload -> ImageUploadHolder(
                ItemImageUploadChatBinding.inflate(inflater,parent,false)
            )

            Type.image_out -> ImageOutHolder(
                ItemImageOutChatBinding.inflate(inflater,parent,false)
            )

            Type.image_in -> ImageInHolder(
                ItemImageInChatBinding.inflate(inflater,parent,false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TextInViewHolder -> holder.bind(getItem(position))
            is TextOutViewHolder -> holder.bind(getItem(position))
            is ImageUploadHolder -> holder.bind(getItem(position))
            is ImageInHolder -> holder.bind(getItem(position))
            is ImageOutHolder-> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).type.ordinal

}