package com.expostore.ui.fragment.chats.dialog.adapter.holder

import com.expostore.databinding.MessageReceivedItemBinding
import com.expostore.model.chats.DataMapping.Message

class ResponseMessageHolder(val binding: MessageReceivedItemBinding) :
    DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        val message = binding.textFile
        message.text = item.text
    }
}