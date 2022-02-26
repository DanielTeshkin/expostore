package com.expostore.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.model.category.CategoryModel
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryRecyclerViewAdapter() :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    var onClick: OnClickRecyclerViewListener? = null

    var list: List<CategoryModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = list!!.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = list[position]
/*
        if (category.products.isNullOrEmpty()) {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }

        holder.name.text = category.name

        val childLayoutManager =
            LinearLayoutManager(holder.rvProduct.context, RecyclerView.HORIZONTAL, false)

        holder.rvProduct.apply {
            layoutManager = childLayoutManager
            adapter = ProductRecyclerViewAdapter(category.products, onClick!!)
            setRecycledViewPool(viewPool)
        }

        holder.btnDetailCategory.setOnClickListener {
            onClick?.onDetailCategoryButton(category)
        }*/
    }
}