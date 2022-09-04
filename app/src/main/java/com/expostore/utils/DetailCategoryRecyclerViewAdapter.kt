package com.expostore.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.data.remote.api.pojo.getcategory.CategoryProduct
import kotlin.collections.ArrayList

class DetailCategoryRecyclerViewAdapter(private val products: ArrayList<CategoryProduct>?, var context: Context) : RecyclerView.Adapter<DetailCategoryRecyclerViewAdapter.DetailCategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    var onClick : OnClickRecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCategoryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detail_product_item, parent, false)
        return DetailCategoryViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class DetailCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      //  var name: TextView = itemView.tv_detail_product_name
       // var price: TextView = itemView.tv_detail_product_price
       // var rvImages: RecyclerView = itemView.rv_detail_product_images
       // var noteTitle: TextView = itemView.tv_detail_product_note_title
      // var note: TextView = itemView.tv_detail_product_note
    }

    override fun onBindViewHolder(holder: DetailCategoryViewHolder, position: Int) {
        val product = products!![position]

       // holder.name.text = product.name
        //holder.price.text = product.price
/*
        holder.itemView.setOnClickListener{
            onClick!!.onDetailCategoryProductItemClick(product.id)
        }*/

        holder

        //val childLayoutManager = LinearLayoutManager(
           // holder.rvImages.context,
           // RecyclerView.HORIZONTAL,
          //  false
       // )

        //todo тестовый костыль
        if (position == 0){
            //holder.noteTitle.visibility = View.VISIBLE
            //holder.note.visibility = View.VISIBLE
            //todo убрать и чекать по наличию отзыва
          //  holder.note.text = "Хочу купить себе на кухню"
        }
        else{
            //holder.noteTitle.visibility = View.GONE
           // holder.note.visibility = View.GONE
        }

        val snapHelper = PagerSnapHelper() // Or PagerSnapHelper
       // snapHelper.attachToRecyclerView(holder.rvImages)

/*        if (!product.images.isNullOrEmpty()) {
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(context, product.images!!, product.id, product.like, onClick!!)
                setRecycledViewPool(viewPool)
            }
        }*/
    }
}


