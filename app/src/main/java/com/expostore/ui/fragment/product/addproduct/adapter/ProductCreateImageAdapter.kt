package com.expostore.ui.fragment.product.addproduct.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.R
import com.expostore.databinding.TenderCreateImageItemBinding
import com.expostore.ui.fragment.product.addproduct.adapter.utils.OnClick

class ProductCreateImageAdapter(private var images: MutableList<String>,private val onClick: OnClick) : RecyclerView.Adapter<ProductCreateImageAdapter.ProductImageViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        return ProductImageViewHolder(
            TenderCreateImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = images.size

    inner class ProductImageViewHolder(val binding: TenderCreateImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var imageView: ImageView = binding.tenderImage


    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        val image = images[position]

        if (position == 0){
            holder.imageView.setImageResource(R.drawable.ic_tender_add_image)
            holder.imageView.setOnClickListener {
                onClick.openGallery()
            }
        }
        else holder.imageView.loadImageProduct(image)
    }

    fun update( uri: String){
        images.add(uri)
        notifyDataSetChanged()
    }

    interface OnClickListener{
        fun addPhoto()
    }


}
fun ImageView.loadImageProduct(url: String){
    Glide.with(this).load(url).centerCrop().into(this)
}