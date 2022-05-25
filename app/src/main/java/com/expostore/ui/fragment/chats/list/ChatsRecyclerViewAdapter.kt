package com.expostore.ui.fragment.chats.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.ui.fragment.chats.dialog.adapter.DiffUtilDialog
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.lastMessage
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.utils.OnClick

import kotlinx.android.synthetic.main.chat_item.view.*

class ChatsRecyclerViewAdapter(private var chats: MutableList<MainChat>, val onClickListener: OnClick) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatsViewHolder(v)
    }
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = chats.size

    inner class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.iv_chat_image
        var name: TextView = itemView.tv_chat_name
        var message: TextView = itemView.tv_chat_message
        var text: TextView = itemView.tv_chat_text
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val chat = chats[position]
        val list=chat.identify()
        holder.name.text = list[1]
        holder.message.text= chat.lastMessage()
        holder.image.loadAvatar(list[2])
        holder.itemView.setOnClickListener {
            onClickListener.onClickChat(position, chat) }
    }

    fun addData(listItems: MutableList<MainChat>) {
         val diffUtil= DiffUtil.calculateDiff(DiffUtil(chats,listItems))
        diffUtil.dispatchUpdatesTo(this)
        chats=listItems
    }

}