package com.expostore.ui.fragment.chats.dialog.adapter.holder

import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FileSentBinding
import com.expostore.model.chats.DataMapping.FileChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.FileRecyclerViewAdapter

class RequestFileHolder(val binding:FileSentBinding):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        binding.message.text=item.text
        val list=ArrayList<FileChat>()
            item.chatFiles!!.map{ list.add(it)}
            binding.rvFile.apply {
                val manager= LinearLayoutManager(context)
                val myAdapter= FileRecyclerViewAdapter(list, context)
                layoutManager= manager
                adapter=myAdapter
            }
    }
}