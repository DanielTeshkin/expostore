package com.expostore.utils

import ProductImageRecyclerViewAdapter
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.expostore.R
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.getcategory.CategoryProductImage
import kotlinx.android.synthetic.main.detail_product_item.view.*
import org.w3c.dom.Text
import kotlin.collections.ArrayList

class DetailCategoryRecyclerViewAdapter(private val products: ArrayList<CategoryProduct>?, var context: Context) : RecyclerView.Adapter<DetailCategoryRecyclerViewAdapter.DetailCategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCategoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detail_product_item, parent, false)
        return DetailCategoryViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class DetailCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tv_detail_product_name
        var price: TextView = itemView.tv_detail_product_price
        var rvImages: RecyclerView = itemView.rv_detail_product_images
        var noteTitle: TextView = itemView.tv_detail_product_note_title
        var note: TextView = itemView.tv_detail_product_note
    }

    override fun onBindViewHolder(holder: DetailCategoryViewHolder, position: Int) {
        val product = products!![position]

        holder.name.text = product.name
        holder.price.text = product.price

        val childLayoutManager = LinearLayoutManager(
            holder.rvImages.context,
            RecyclerView.HORIZONTAL,
            false
        )

        if (position == 0){
            holder.noteTitle.visibility = View.VISIBLE
            holder.note.visibility = View.VISIBLE
            holder.note.text = "Хочу купить себе на кухню"
        }
        else{
            holder.noteTitle.visibility = View.GONE
            holder.note.visibility = View.GONE
        }

        val testImages = arrayListOf(CategoryProductImage(null,null),CategoryProductImage(null,null),CategoryProductImage(null,null))

        val snapHelper = PagerSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(holder.rvImages)

        if (!product.images.isNullOrEmpty()) {
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(product.images, context)
                setRecycledViewPool(viewPool)
            }
        }
        else {
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(testImages, context)
                setRecycledViewPool(viewPool)
            }
        }
    }
}


