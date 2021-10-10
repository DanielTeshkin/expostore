package com.expostore.utils

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import kotlinx.android.synthetic.main.tender_create_image_item.view.*

class TenderCreateImageRecyclerViewAdapter(private var images: ArrayList<Bitmap>) : RecyclerView.Adapter<TenderCreateImageRecyclerViewAdapter.TenderImageViewHolder>() {

    var onClick : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenderImageViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.tender_create_image_item, parent, false)
        return TenderImageViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = images.size

    inner class TenderImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.tender_image
    }

    override fun onBindViewHolder(holder: TenderImageViewHolder, position: Int) {
        val image = images[position]

        if (position == 0){
            holder.imageView.setImageResource(R.drawable.ic_tender_add_image)
            holder.imageView.setOnClickListener {
                onClick!!.addPhoto()
            }
        }
        else holder.imageView.setImageBitmap(image)
    }

    interface OnClickListener{
        fun addPhoto()
    }
}