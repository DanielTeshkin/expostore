package com.expostore.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.expostore.databinding.ItemImageBinding
import com.expostore.extension.load
import com.expostore.model.product.ProductModel

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

/*            binding.root.setOnClickWithDelay {
                handleImageClick(it.context, items, absoluteAdapterPosition)
            }*/
        }
    }

/*    private fun handleImageClick(context: Context, items: List<String>, position: Int) {
        StfalconImageViewer.Builder(context, items) { view, image ->
            Glide.with(context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
            .withStartPosition(position)
            .show(true)
    }*/
}