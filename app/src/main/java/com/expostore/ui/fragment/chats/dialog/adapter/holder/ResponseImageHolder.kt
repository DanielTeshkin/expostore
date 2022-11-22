package com.expostore.ui.fragment.chats.dialog.adapter.holder

import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ImageReceviedItemBinding
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.ImageMessageRecyclerViewAdapter
import com.expostore.utils.OnClickImage

class ResponseImageHolder(val binding:ImageReceviedItemBinding, private  val onClickImage: OnClickImage):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        processor.checkCondition(condition = {item.text.isEmpty()},
            actionFalse = {  binding.textImage.text=item.text},
            actionTrue = {binding.textImage.isVisible=false}
        )
        binding.imageRec.apply {
                val list=ArrayList<String>()
                item.images?.map{ it.file.let { it1 -> list.add(it1) } }
                val gridLayoutManager= LinearLayoutManager(context)
                val imageAdapter= ImageMessageRecyclerViewAdapter(list,onClickImage,"network")
                layoutManager=gridLayoutManager
                adapter=imageAdapter
            }
    }
}

class ResponseLocalImageHolder(val binding:ImageReceviedItemBinding, private  val onClickImage: OnClickImage):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        processor.checkCondition(condition = {item.text.isEmpty()},
            actionFalse = {  binding.textImage.text=item.text},
            actionTrue = {binding.textImage.isVisible=false}
        )
        binding.imageRec.apply {
            val list=ArrayList<String>()
            item.images?.map{ it.file.let { it1 -> list.add(it1) } }
            val gridLayoutManager= LinearLayoutManager(context)
            val imageAdapter= ImageMessageRecyclerViewAdapter(list,onClickImage,"local")
            layoutManager=gridLayoutManager
            adapter=imageAdapter
        }
    }
}