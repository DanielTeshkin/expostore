package com.expostore.ui.fragment.chats.dialog.adapter.holder

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ImageReceviedItemBinding
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.ImageMessageRecyclerViewAdapter
import com.expostore.utils.OnClickImage

class ResponseImageHolder(val binding:ImageReceviedItemBinding, private  val onClickImage: OnClickImage):DialogViewHolder(binding.root) {
    override fun bind(item: Message) {
        val message = binding.textImage
        message.text = item.text
        Log.i("stroke","a")

            binding.imageRec.apply {
                val list=ArrayList<String>()
                item.images?.map{ it.file?.let { it1 -> list.add(it1) } }
                val gridLayoutManager= LinearLayoutManager(context)
                val imageAdapter= ImageMessageRecyclerViewAdapter(list,onClickImage)
                layoutManager=gridLayoutManager
                adapter=imageAdapter
            }
    }
}