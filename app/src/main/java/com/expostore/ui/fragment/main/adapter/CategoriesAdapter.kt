package com.expostore.ui.fragment.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.CategoryItemBinding

import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel

/**
 * @author Fedotov Yakov
 */
class CategoriesAdapter :
    ListAdapter<SelectionModel, CategoriesAdapter.CategoryViewHolder>(CATEGORIES_DIFF_UTIL) {
    var onCategoryClickListener: ((SelectionModel) -> Unit)? = null
    var onProductClickListener: ((ProductModel) -> Unit)? = null

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

        fun bind(item: SelectionModel) {
            binding.run {
                categoryName.text = item.name
                categoryProducts.adapter = ProductAdapter().apply {
                    submitList(item.products)
                    onItemClicked = { onProductClickListener?.invoke(it) }
                }
            }
        }
    }

    companion object {
        private val CATEGORIES_DIFF_UTIL = object : DiffUtil.ItemCallback<SelectionModel>() {
            override fun areItemsTheSame(oldItem: SelectionModel, newItem: SelectionModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem:SelectionModel, newItem: SelectionModel) =
                oldItem == newItem
        }
    }
}