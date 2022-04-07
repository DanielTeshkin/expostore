package com.expostore.ui.fragment.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.expostore.databinding.CategoryItemBinding
import com.expostore.databinding.ProductItemBinding
import com.expostore.extension.load
import com.expostore.model.product.ProductModel

/**
 * @author Fedotov Yakov
 */
class ProductAdapter: ListAdapter<ProductModel, ProductAdapter.ProductsViewHolder>(ProductsDiffCallback) {
    var onItemClicked: ((ProductModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        getItem(position).also(holder::bind)
    }

    inner class ProductsViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                root.setOnClickListener {
                    onItemClicked?.invoke(currentList[adapterPosition])
                }
            }
        }

        fun bind(item: ProductModel) {
            binding.run {
                productName.text = item.name
                item.images.firstOrNull()?.file?.let {
                    productImage.load(it)
                }
            }
        }
    }

    object ProductsDiffCallback : DiffUtil.ItemCallback<ProductModel>() {
        override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel) =
            oldItem == newItem
    }
}