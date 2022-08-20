package com.expostore.utils

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.R
import com.expostore.databinding.MyTenderItemBinding
import com.expostore.databinding.TenderCreateImageItemBinding
import com.expostore.extension.load
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.fragment.profile.profile_edit.click
import kotlinx.android.synthetic.main.tender_create_image_item.view.*

class TenderCreateImageRecyclerViewAdapter(private var images: MutableList<String>) : RecyclerView.Adapter<TenderCreateImageRecyclerViewAdapter.TenderImageViewHolder>() {

    var onClick : OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenderImageViewHolder {
       return TenderImageViewHolder(
           TenderCreateImageItemBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       ))
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = images.size

    inner class TenderImageViewHolder(val binding:TenderCreateImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var imageView: ImageView = binding.tenderImage


    }

    override fun onBindViewHolder(holder: TenderImageViewHolder, position: Int) {
        val image = images[position]

        if (position == 0){
            holder.imageView.setImageResource(R.drawable.ic_tender_add_image)
            holder.imageView.click {
                onClick!!.addPhoto()
            }
        }
        else{
            holder.imageView.loadImageTender(image)
            holder.binding.deleteImage.apply {
                visibility= View.VISIBLE
                click { removeAt(position)
                 onClick!!.removePhoto(position-1)
                }
            }
        }
    }

    fun update( uri: String){
        images.add(uri)
        notifyDataSetChanged()
    }

    interface OnClickListener{
        fun addPhoto()
        fun removePhoto(index: Int)
    }
    private fun removeAt(index: Int) {
        images.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,itemCount);
    }

}
fun ImageView.loadImageTender(uri: String){
    Glide.with(this).load(uri).centerCrop().into(this)
}