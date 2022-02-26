package com.expostore.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.product_item.view.*

class ProductRecyclerViewAdapter(private val products: ArrayList<CategoryProduct>?, var onClick: OnClickRecyclerViewListener) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products!![position]
/*
        holder.name.text = product.name

        if (!product.images.isNullOrEmpty()) {
            if (product.images!![0].file != null)
                Picasso.get().load(product.images!![0].file).into(holder.image)
        }

        holder.itemView.setOnClickListener {
            onClick.onProductClick(product.id)
        }*/
    }

}