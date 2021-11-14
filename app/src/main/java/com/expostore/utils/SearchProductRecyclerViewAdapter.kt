package com.expostore.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getlistproduct.Product
import kotlinx.android.synthetic.main.search_product_item.view.*
import kotlin.collections.ArrayList

class SearchProductRecyclerViewAdapter(private val products: ArrayList<Product>?, var context: Context) : RecyclerView.Adapter<SearchProductRecyclerViewAdapter.SearchProductViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    var onClick : OnClickRecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_product_item, parent, false)
        return SearchProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class SearchProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tv_search_product_name
        var price: TextView = itemView.tv_search_product_price
        var rvImages: RecyclerView = itemView.rv_search_product_images
        var description: TextView = itemView.tv_search_product_description
        var address: TextView = itemView.tv_search_product_address
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        val product = products!![position]

        holder.name.text = product.name
        holder.price.text = product.price
        holder.description.text = product.name
        holder.address.text = product.name

        holder.itemView.setOnClickListener{
            onClick!!.onDetailCategoryProductItemClick(product.id)
        }

        val childLayoutManager = LinearLayoutManager(
            holder.rvImages.context,
            RecyclerView.HORIZONTAL,
            false
        )

        holder.rvImages.onFlingListener = null
        val snapHelper = PagerSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(holder.rvImages)

        if (!product.images.isNullOrEmpty()) {
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(context, product.images, product.id, product.like, onClick!!)
                setRecycledViewPool(viewPool)
            }
        }
    }
}


