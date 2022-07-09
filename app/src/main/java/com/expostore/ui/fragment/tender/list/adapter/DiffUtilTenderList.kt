package com.expostore.ui.fragment.tender.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.expostore.model.chats.DataMapping.Message
import com.expostore.model.tender.TenderModel

class DiffUtilTenderList(private val oldList: MutableList<TenderModel>, private val newList: MutableList<TenderModel>): DiffUtil.Callback() {
    override fun getOldListSize(): Int =oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =oldList[oldItemPosition].id==newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition]==newList[newItemPosition]
    }
}