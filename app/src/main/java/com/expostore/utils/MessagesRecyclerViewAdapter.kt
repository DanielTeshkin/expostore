package com.expostore.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//import com.statist.pojo.getmessages.Message
import kotlinx.android.synthetic.main.message_received_item.view.*
import kotlinx.android.synthetic.main.message_sent_item.view.*
import kotlin.collections.ArrayList

const val MESSAGE_OPERATOR = 1
const val MY_MESSAGE = 0

//class MessagesRecyclerViewAdapter(var messages: ArrayList<Message>) : RecyclerView.Adapter<MessagesRecyclerViewAdapter.BaseViewHolder<*>>() {
//
//    override fun getItemCount(): Int = messages.size
//
//    override fun getItemViewType(position: Int): Int = messages[position].type
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
//        return when (viewType){
//
//            MESSAGE_OPERATOR -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.message_received_item, parent, false)
//                ReceivedViewHolder(view)
//            }
//
//            MY_MESSAGE -> {
//                val view = LayoutInflater.from(parent.context).inflate(R.layout.message_sent_item, parent, false)
//                SentViewHolder(view)
//            }
//            else -> throw IllegalArgumentException("Invalid view type")
//        }
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
//        val element = messages[position]
//
//        when(holder){
//            is ReceivedViewHolder -> holder.bind(element)
//            is SentViewHolder -> holder.bind(element)
//            else -> throw IllegalArgumentException()
//        }
//    }
//
//    inner class ReceivedViewHolder(itemView: View) : BaseViewHolder<Message>(itemView) {
//        private val message = itemView.text_message_received
//        override fun bind(item: Message) {
//            message.text = item.message
//        }
//    }
//
//    inner class SentViewHolder(itemView: View) : BaseViewHolder<Message>(itemView) {
//        private val message = itemView.text_message_sent
//        override fun bind(item: Message) {
//            message.text = item.message
//        }
//    }
//
//    abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        abstract fun bind(item: T)
//    }
//}