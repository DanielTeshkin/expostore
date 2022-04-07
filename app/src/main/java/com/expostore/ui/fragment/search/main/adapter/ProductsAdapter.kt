package com.expostore.ui.fragment.search.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import com.expostore.databinding.SearchProductItemBinding
import com.expostore.extension.dp
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.ImageAdapter

class ProductsAdapter :
    PagingDataAdapter<ProductModel, ProductsAdapter.ProductsViewHolder>(PRODUCTS_DIFF_UTIL) {

    var onItemClickListener: ((ProductModel) -> Unit)? = null
    var onLikeItemClickListener: ((String) -> Unit)? = null
    var onCallItemClickListener: ((String) -> Unit)? = null

    private val queueLikes = mutableMapOf<String, (() -> Unit)>()

    fun processLike(id: String, isSuccess: Boolean) {
        if(!isSuccess) {
            queueLikes[id]?.invoke()
        }
        queueLikes.remove(id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            SearchProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position) ?: ProductModel())
    }

    inner class ProductsViewHolder(private val binding: SearchProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = ImageAdapter()

        init {
            binding.apply {
                viewPager.adapter = adapter
                viewPager.setPageTransformer(MarginPageTransformer(PAGE_PADDING.dp))
                viewPager.children.find { it is RecyclerView }?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                adapter.onItemClickListener = {
                    getItem(absoluteAdapterPosition)?.let { model ->
                        onItemClickListener?.invoke(model)
                    }
                }
                root.setOnClickListener {
                    getItem(absoluteAdapterPosition)?.let { model ->
                        onItemClickListener?.invoke(model)
                    }
                }
                like.setOnClickListener {
                    getItem(absoluteAdapterPosition)?.let { model ->
                        if (queueLikes[model.id] == null)
                            it.isSelected = !it.isSelected
                        onLikeItemClickListener?.invoke(model.id)
                        queueLikes[model.id] = { it.isSelected = !it.isSelected }
                    }
                }
                call.setOnClickListener {
                    getItem(absoluteAdapterPosition)?.let { model ->
                        onCallItemClickListener?.invoke(model.author.username)
                    }
                }
            }
        }

        fun bind(item: ProductModel) {
            binding.apply {
                like.isSelected = item.isLiked
                name.text = item.name
                price.text = item.price
                description.text = item.shortDescription
                address.text = item.promotion
            }
            adapter.items = item.images.map { it.file }
        }
    }

    companion object {
        private const val PAGE_PADDING = 20

        private val PRODUCTS_DIFF_UTIL = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
                oldItem == newItem
        }
    }

}