package com.expostore.ui.fragment.chats.dialog.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.chats.DataMapping.Message

abstract class DialogViewHolder(view: View): RecyclerView.ViewHolder(view) {
     abstract fun bind(item: Message)
}