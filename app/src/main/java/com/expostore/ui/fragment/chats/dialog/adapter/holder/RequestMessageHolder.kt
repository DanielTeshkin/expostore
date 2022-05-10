package com.expostore.ui.fragment.chats.dialog.adapter.holder

import com.expostore.databinding.MessageSentItemBinding
import com.expostore.model.chats.DataMapping.Message

class RequestMessageHolder(val binding: MessageSentItemBinding): DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        val message = binding.textMessageSent
        message.text = item.text
    }
}