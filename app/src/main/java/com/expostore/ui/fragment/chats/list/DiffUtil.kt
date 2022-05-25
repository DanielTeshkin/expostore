package com.expostore.ui.fragment.chats.list

import androidx.recyclerview.widget.DiffUtil
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.DataMapping.Message

class DiffUtil(private val oldList: MutableList<MainChat>, private val newList: MutableList<MainChat>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean= oldList[oldItemPosition].id==newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =oldList[oldItemPosition]==newList[newItemPosition]
}