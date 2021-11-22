package com.expostore.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.getchats.Chat
import com.expostore.api.pojo.getreviews.Review
import kotlinx.android.synthetic.main.chat_item.view.*
import kotlinx.android.synthetic.main.product_review_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ChatsRecyclerViewAdapter(private val chats: ArrayList<Chat>) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsViewHolder>() {

    var onClick : OnClickRecyclerViewListener? = null

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

        holder.name.text = chat.seller.firstName + " " + chat.seller.lastName

        holder.itemView.setOnClickListener{
            onClick!!.onChatClick()
        }


    }

    fun removeAt(index: Int) {
        chats.removeAt(index)   // items is a MutableList
        notifyItemRemoved(index)
    }
}