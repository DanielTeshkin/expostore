package com.expostore.ui.fragment.chats.dialog.adapter.holder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FileReceivedBinding
import com.expostore.model.chats.DataMapping.FileChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.FileRecyclerViewAdapter

class ResponseFileHolder(val binding: FileReceivedBinding):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        processor.checkCondition(condition = {item.text.isEmpty()},
            actionFalse = {  binding.textMessage.text=item.text},
            actionTrue = {binding.textMessage.isVisible=false}
        )
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