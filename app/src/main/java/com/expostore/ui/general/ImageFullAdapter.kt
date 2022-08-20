package com.expostore.ui.general

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.ItemImageBinding
import com.expostore.databinding.ItemImageFullBinding
import com.expostore.extension.load


class ImageFullAdapter : RecyclerView.Adapter<ImageFullAdapter.ImageFullViewHolder>() {

    var onItemClickListener: (() -> Unit)? = null

    var items: List<String> = emptyList()
        set(value) {
            field = value
            if (field.isNotEmpty()) notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageFullViewHolder(
            ItemImageFullBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImageFullViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount() = items.size


    inner class ImageFullViewHolder(private val binding: ItemImageFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.invoke()
            }
        }

        fun bind(url: String) {
            binding.image.load(url)

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