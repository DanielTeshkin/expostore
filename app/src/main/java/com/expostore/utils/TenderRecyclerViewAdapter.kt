package com.expostore.utils

import TenderImageRecyclerViewAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.gettenderlist.Tender
import kotlinx.android.synthetic.main.search_product_item.view.*
import kotlinx.android.synthetic.main.tender_item.view.*

class TenderRecyclerViewAdapter(private var tenders: ArrayList<Tender>) : RecyclerView.Adapter<TenderRecyclerViewAdapter.TenderViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    var onClick : OnClickRecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_product_item, parent, false)
        return TenderViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = tenders.size

    inner class TenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tv_search_product_name
        var price: TextView = itemView.tv_search_product_price
        var rvImages: RecyclerView = itemView.rv_search_product_images
        var description: TextView = itemView.tv_search_product_description
        var address: TextView = itemView.tv_search_product_address

//        var count: TextView = itemView.tv_tender_count
//        var location: TextView = itemView.tv_tender_location
    }

    override fun onBindViewHolder(holder: TenderViewHolder, position: Int) {
        val tender = tenders[position]

        holder.name.text = tender.title
        holder.price.text = tender.priceUpTo
        holder.description.text = tender.description
        holder.address.text = tender.title

        //holder.count.text = "Не вводит пользователь"
        //holder.location.text = tender.location

//        holder.itemView.setOnClickListener{
//            onClick!!.onDetailCategoryProductItemClick(product.id)
//        }

        val childLayoutManager = LinearLayoutManager(
            holder.rvImages.context,
            RecyclerView.HORIZONTAL,
            false
        )

        holder.rvImages.onFlingListener = null
        val snapHelper = PagerSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(holder.rvImages)

        if (!tender.images.isNullOrEmpty()) {
            holder.rvImages.apply {
                layoutManager = childLayoutManager
                adapter = ProductImageRecyclerViewAdapter(context, tender.images, null, null, null)
                setRecycledViewPool(viewPool)
            }
        }
    }

}