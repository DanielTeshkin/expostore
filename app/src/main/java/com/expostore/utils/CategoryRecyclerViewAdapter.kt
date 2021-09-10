package com.expostore.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.getcategory.Category
import kotlinx.android.synthetic.main.category_item.view.*
import kotlin.collections.ArrayList

class CategoryRecyclerViewAdapter(private val categories: ArrayList<Category>?, var context: Context) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    var onClick : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = categories!!.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tv_category_name
        var rvProduct: RecyclerView = itemView.rv_category_products
        var btnDetailCategory: TextView = itemView.btn_category_open
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories!![position]

        holder.name.text = category.name

        val childLayoutManager = LinearLayoutManager(
            holder.rvProduct.context,
            RecyclerView.HORIZONTAL,
            false
        )

        holder.rvProduct.apply {
            layoutManager = childLayoutManager
            adapter = ProductRecyclerViewAdapter(category.products)
            setRecycledViewPool(viewPool)
        }

        holder.btnDetailCategory.setOnClickListener {
            onClick?.onDetailCategoryButton(category)
        }
    }

    interface OnClickListener{
        fun onDetailCategoryButton(category: Category)
    }
}