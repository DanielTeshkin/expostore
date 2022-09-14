package com.expostore.ui.fragment.chats.dialog.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.base.ConditionProcessor

abstract class DialogViewHolder(view: View): RecyclerView.ViewHolder(view) {
   protected  val  processor= ConditionProcessor()
     abstract fun bind(item: Message)
}