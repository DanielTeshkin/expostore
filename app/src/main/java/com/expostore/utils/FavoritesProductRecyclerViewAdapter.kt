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
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import kotlinx.android.synthetic.main.detail_product_item.view.*

class FavoritesProductRecyclerViewAdapter(private val products: ArrayList<GetFavoritesListResponseData>?, var context: Context) : RecyclerView.Adapter<FavoritesProductRecyclerViewAdapter.FavoritesProductViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    var onClick : OnClickRecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detail_product_item, parent, false)
        return FavoritesProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class FavoritesProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tv_detail_product_name
        var price: TextView = itemView.tv_detail_product_price
        var rvImages: RecyclerView = itemView.rv_detail_product_images
        var noteTitle: TextView = itemView.tv_detail_product_note_title
        var note: TextView = itemView.tv_detail_product_note
    }

    override fun onBindViewHolder(holder: FavoritesProductViewHolder, position: Int) {
        val product = products!![position].product

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

        val testImages = arrayListOf(
            CategoryProductImage(null,null),
            CategoryProductImage(null,null),
            CategoryProductImage(null,null)
        )

        val snapHelper = PagerSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(holder.rvImages)

        if (!product.images.isNullOrEmpty()) {
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(context, product.images!!, product.id, product.like, onClick!!)
                setRecycledViewPool(viewPool)
            }
        }
        else {
            product.images = testImages
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(context, testImages, product.id, product.like, onClick!!)
                setRecycledViewPool(viewPool)
            }
        }
    }
}

interface OnClickListener {
    fun onLikeClick(like: Boolean, id: String?)
}