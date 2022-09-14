package com.expostore.ui.fragment.chats.dialog.adapter.holder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FileSentBinding
import com.expostore.model.chats.DataMapping.FileChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.fragment.chats.dialog.adapter.FileRecyclerViewAdapter

class RequestFileHolder(val binding:FileSentBinding):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        processor.checkCondition(condition = {item.text.isEmpty()},
            actionFalse = {  binding.message.text=item.text},
            actionTrue = {binding.message.isVisible=false}
        )
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