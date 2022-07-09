package com.expostore.ui.fragment.specifications.adapter.holder

import androidx.recyclerview.widget.DiffUtil
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.chats.DataMapping.Message

class DiffUtil(private val oldList: MutableList<ProductCategoryModel>, private val newList: MutableList<ProductCategoryModel>): DiffUtil.Callback() {
    override fun getOldListSize(): Int= oldList.size

    override fun getNewListSize(): Int =newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].id==newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition]==newList[newItemPosition]
}