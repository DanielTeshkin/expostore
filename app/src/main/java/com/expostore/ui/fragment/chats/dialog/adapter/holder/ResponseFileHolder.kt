package com.expostore.ui.fragment.chats.dialog.adapter.holder

import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FileReceivedBinding
import com.expostore.model.chats.DataMapping.FileChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.FileRecyclerViewAdapter

class ResponseFileHolder(val binding: FileReceivedBinding):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        binding.textMessage.text = item.text
        binding.rvFiles.apply {
            val list = ArrayList<FileChat>()
            item.chatFiles!!.map { list.add(it) }
            val manager = LinearLayoutManager(context)
            val myAdapter = FileRecyclerViewAdapter(list, context)
            layoutManager = manager
            adapter = myAdapter

        }
    }
}