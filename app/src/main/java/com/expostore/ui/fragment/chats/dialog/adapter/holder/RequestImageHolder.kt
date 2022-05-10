package com.expostore.ui.fragment.chats.dialog.adapter.holder

import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ImageSentItemBinding
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.ImageMessageRecyclerViewAdapter
import com.expostore.utils.OnClickImage

class RequestImageHolder(val binding: ImageSentItemBinding, private val onClickImage: OnClickImage):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        val message = binding.textMessageS
       message.text = item.text
        binding.imageS.apply {
                val list=ArrayList<String>()
                item.images?.map {list.add(it.file)  }
                val gridLayoutManager= LinearLayoutManager(context)
                val imageAdapter= ImageMessageRecyclerViewAdapter(list,onClickImage)
                layoutManager=gridLayoutManager
                adapter=imageAdapter
            }
    }
}