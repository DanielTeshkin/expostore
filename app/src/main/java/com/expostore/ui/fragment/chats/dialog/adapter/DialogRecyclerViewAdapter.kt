package com.expostore.ui.fragment.chats.dialog.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.holder.DialogViewHolder
import com.expostore.utils.OnClickImage


class DialogRecyclerViewAdapter(private var messages:MutableList<Message>,
                                private val username:String, val context: Context,
                                val onClickImage: OnClickImage
)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var diffUtil:DiffUtil.DiffResult
    private val typeHolder=TypeHolder(onClickImage)
    override fun getItemViewType(position: Int): Int =typeHolder.getType(username,messages[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return typeHolder.createHolder(viewType,parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
           is DialogViewHolder -> holder.bind(messages[position])
       }

    }

    override fun getItemCount(): Int {
        return messages.size
    }


    fun addData(listItems: MutableList<Message>) {
            diffUtil=DiffUtil.calculateDiff(DiffUtilDialog(messages,listItems))
            diffUtil.dispatchUpdatesTo(this)
            messages=listItems
        }
         fun addMessage(message: Message){
             val list= ArrayList<Message>()
             list.addAll(messages)
             list.add(message)
             diffUtil=DiffUtil.calculateDiff(DiffUtilDialog(messages,list))
             diffUtil.dispatchUpdatesTo(this)
             messages=list

         }

    }