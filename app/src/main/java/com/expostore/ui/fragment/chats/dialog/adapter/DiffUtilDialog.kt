package com.expostore.ui.fragment.chats.dialog.adapter

import androidx.recyclerview.widget.DiffUtil
import com.expostore.model.chats.DataMapping.Message

class DiffUtilDialog(private val oldList: MutableList<Message>, private val newList: MutableList<Message>): DiffUtil.Callback() {
    override fun getOldListSize(): Int =oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =oldList[oldItemPosition].id==newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition]==newList[newItemPosition]
    }
}