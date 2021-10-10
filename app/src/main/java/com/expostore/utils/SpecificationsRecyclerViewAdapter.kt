package com.expostore.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.productcategory.ProductCategory
import kotlinx.android.synthetic.main.specification_item.view.*

class SpecificationsRecyclerViewAdapter(private var specifications: ArrayList<ProductCategory>) : RecyclerView.Adapter<SpecificationsRecyclerViewAdapter.SpecificationsViewHolder>() {

    var onClick : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.specification_item, parent, false)
        return SpecificationsViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = specifications.size

    inner class SpecificationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.specification_name
        var checkBox: CheckBox = itemView.specification_checkbox
    }

    override fun onBindViewHolder(holder: SpecificationsViewHolder, position: Int) {
        val specification = specifications[position]

        holder.name.text = specification.name
        holder.checkBox.setOnClickListener{
            onClick!!.addCategory(holder.checkBox.isChecked, specification.id)
        }
    }

    interface OnClickListener{
        fun addCategory(isChecked: Boolean, id: String?)
    }
}