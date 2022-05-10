package com.expostore.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.response.CategoryResponse
import com.expostore.api.response.ProductResponse

import kotlinx.android.synthetic.main.category_item.view.*

class CategoryRecyclerViewAdapter(private val product_list:MutableList<CategoryResponse>, val context: Context) :
    RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>() {



    var onClick: OnClickRecyclerViewListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = product_list!!.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

          fun bind(item:CategoryResponse){
              val list= ArrayList<ProductResponse>()
              Log.i("qqq",itemCount.toString())
             item.products?.map { list.add(it) }
              itemView.category_name.text=item.name
              val recyclerView=itemView.category_products
              val linearLayoutManager=LinearLayoutManager(context,RecyclerView.HORIZONTAL, false)
              val adapter=ProductRecyclerViewAdapter(list)
              recyclerView.layoutManager=linearLayoutManager
              recyclerView.adapter=adapter
          }
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(product_list[position])
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