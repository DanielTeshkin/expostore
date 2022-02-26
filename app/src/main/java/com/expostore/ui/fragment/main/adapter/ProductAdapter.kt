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
import com.expostore.model.category.CategoryProductModel

/**
 * @author Fedotov Yakov
 */
class ProductAdapter: ListAdapter<CategoryProductModel, ProductAdapter.ProductsViewHolder>(ProductsDiffCallback) {
    var onItemClicked: ((CategoryProductModel) -> Unit)? = null

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

        fun bind(item: CategoryProductModel) {
            binding.run {
                productName.text = item.name
                item.images.firstOrNull()?.file?.let {
                    Glide.with(root.context)
                        .load(it)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(productImage)
                }
            }
        }
    }

    object ProductsDiffCallback : DiffUtil.ItemCallback<CategoryProductModel>() {
        override fun areItemsTheSame(oldItem: CategoryProductModel, newItem: CategoryProductModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CategoryProductModel, newItem: CategoryProductModel) =
            oldItem == newItem
    }
}