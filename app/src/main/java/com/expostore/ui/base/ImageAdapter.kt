package com.expostore.ui.base

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.expostore.databinding.ItemImageBinding
import com.expostore.extension.load
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.profile.profile_edit.click

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var onItemClickListener: (() -> Unit)? = null
    var items: List<String> = emptyList()
        set(value) {
            field = value
            if (field.isNotEmpty()) notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount() = items.size


    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke()
            }
        }

        fun bind(url: String) {
            binding.icon.load(url)

        }
    }


}
class ImageItemAdapter : RecyclerView.Adapter<ImageItemAdapter.ImageViewHolder>() {

    var openImageOnFullScren: ((Bitmap) -> Unit)? = null
    var items: List<String> = emptyList()
        set(value) {
            field = value
            if (field.isNotEmpty()) notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
        holder.binding.icon.click {
            val drawable = holder.binding.icon.drawable
            val bitmap= drawable.toBitmap()
            openImageOnFullScren?.invoke(bitmap)
        }

    }

    override fun getItemCount() = items.size


    inner class ImageViewHolder( val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) { binding.icon.load(url) }
    }

}
