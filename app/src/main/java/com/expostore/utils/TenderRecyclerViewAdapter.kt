package com.expostore.utils

import TenderImageRecyclerViewAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.gettenderlist.Tender
import kotlinx.android.synthetic.main.tender_item.view.*

class TenderRecyclerViewAdapter(private var tenders: ArrayList<Tender>) : RecyclerView.Adapter<TenderRecyclerViewAdapter.TenderViewHolder>() {

    var onClick : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenderViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.tender_item, parent, false)
        return TenderViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = tenders.size

    inner class TenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.tv_tender_name
        var price: TextView = itemView.tv_tender_price
        var description: TextView = itemView.tv_tender_description
        var count: TextView = itemView.tv_tender_count
        var location: TextView = itemView.tv_tender_location
        var images: RecyclerView = itemView.rv_tender_images
    }

    override fun onBindViewHolder(holder: TenderViewHolder, position: Int) {
        val tender = tenders[position]

        holder.name.text = tender.title
        holder.price.text = tender.priceUpTo
        holder.description.text = tender.description
        holder.count.text = "Не вводит пользователь"
        holder.location.text = tender.location

        if (!tender.images.isNullOrEmpty()) {
            holder.images.apply {
                layoutManager = LinearLayoutManager(holder.images.context, RecyclerView.HORIZONTAL, false)
                adapter = TenderImageRecyclerViewAdapter(tender.images)
            }
        }
    }

    interface OnClickListener{
        fun addPhoto()
    }
}