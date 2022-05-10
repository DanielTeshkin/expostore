package com.expostore.ui.fragment.chats.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.expostore.R
import com.expostore.databinding.*
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.holder.*
import com.expostore.utils.OnClickImage

class TypeHolder(  private val onClickImage: OnClickImage)
{
    fun getType(username: String, message: Message):Int{
        return if(message.author==username&&message.images?.size==0
            && message.chatFiles!!.size==0
        ) R.layout.message_sent_item
        else if(message.author!=username&& message.images?.size==0
            && message.chatFiles!!.size==0
        ) R.layout.message_received_item
        else if(message.author==username&& message.images?.size!=0
            && message.chatFiles!!.size==0
        ) R.layout.image_sent_item
        else if(message.author!=username&& message.images?.size!=0
            && message.chatFiles!!.size==0
        ) R.layout.image_recevied_item
        else if(message.author!=username&& message.chatFiles!!.size >0
            && message.images?.size==0
        ) R.layout.file_received
        else if(message.author==username&& message.chatFiles!!.size >0
            && message.images?.size==0
        ) R.layout.file_sent
        else{ 4}
    }

    fun createHolder( type:Int,parent: ViewGroup): DialogViewHolder {
     return when(type){
            R.layout.message_sent_item -> RequestMessageHolder(MessageSentItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false))
            R.layout.message_received_item ->ResponseMessageHolder(MessageReceivedItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false))
            R.layout.image_recevied_item ->ResponseImageHolder(ImageReceviedItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false),onClickImage)
            R.layout.image_sent_item->RequestImageHolder(
                ImageSentItemBinding.inflate(LayoutInflater.from(parent.context), parent,
                false),onClickImage)
            R.layout.file_sent->RequestFileHolder( FileSentBinding.inflate(LayoutInflater.from(parent.context), parent,
                false))
            R.layout.file_received->ResponseFileHolder(FileReceivedBinding.inflate(LayoutInflater.from(parent.context), parent,
                false))
         else-> ResponseFileHolder(FileReceivedBinding.inflate(LayoutInflater.from(parent.context), parent,
             false))
        }
    }
}