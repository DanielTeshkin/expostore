package com.expostore.ui.fragment.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.CategoryItemBinding
import com.expostore.model.category.CategoryModel
import com.expostore.model.category.CategoryProductModel

/**
 * @author Fedotov Yakov
 */
class CategoriesAdapter :
    ListAdapter<CategoryModel, CategoriesAdapter.CategoryViewHolder>(CategoriesDiffCallback) {
    var onCategoryClickListener: ((CategoryModel) -> Unit)? = null
    var onProductClickListener: ((CategoryProductModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        getItem(position).also(holder::bind)
    }

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.run {
                root.setOnClickListener {
                    onCategoryClickListener?.invoke(currentList[adapterPosition])
                }
            }
        }

        fun bind(item: CategoryModel) {
            binding.run {
                categoryName.text = item.name
                categoryProducts.adapter = ProductAdapter().apply {
                    submitList(item.products)
                    onItemClicked = { onProductClickListener?.invoke(it) }
                }
            }
        }
    }

    object CategoriesDiffCallback : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel) =
            oldItem == newItem
    }
}