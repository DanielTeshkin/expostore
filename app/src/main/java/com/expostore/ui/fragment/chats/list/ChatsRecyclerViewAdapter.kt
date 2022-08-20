package com.expostore.ui.fragment.chats.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.databinding.ChatItemBinding
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.chats.dialog.adapter.DiffUtilDialog
import com.expostore.utils.OnClick

import kotlinx.android.synthetic.main.chat_item.view.*

class ChatsRecyclerViewAdapter(private var chats: MutableList<MainChat>, val onClickListener: OnClick) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
       return  ChatsViewHolder(ChatItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = chats.size

    inner class ChatsViewHolder( val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {

        fun bind(item: MainChat) {
            val list = item.identify()
            val image: ImageView = binding.ivChatImage
            val name: TextView = binding.tvChatName
            val message: TextView = binding.tvChatMessage
            name.text = list[1]
            message.text = item.firstMessage()
            image.loadChatAvatar(list[2])
            onLongClick(binding.root)
        }

        override fun onLongClick(p0: View?): Boolean {
                        return false
        }
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val chat = chats[position]
        holder.bind(chat)
        holder.binding.root.setOnClickListener {
            onClickListener.onClickChat(position, chat) }
    }

    fun addData(listItems: MutableList<MainChat>) {
         val diffUtil= DiffUtil.calculateDiff(DiffUtil(chats,listItems))
        diffUtil.dispatchUpdatesTo(this)
        chats=listItems
    }

}