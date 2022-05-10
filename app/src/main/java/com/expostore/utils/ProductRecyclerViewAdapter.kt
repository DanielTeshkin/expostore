package com.expostore.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.response.ProductResponse
import com.expostore.extension.load
import kotlinx.android.synthetic.main.product_item.view.*

class ProductRecyclerViewAdapter(private val products: ArrayList<ProductResponse>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                  fun bind(item:ProductResponse){
                      itemView.product_name.text=item.name
                      itemView.product_image.load(item.images?.get(0)!!.file!!)
                  }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
       holder.bind(products!![position])
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