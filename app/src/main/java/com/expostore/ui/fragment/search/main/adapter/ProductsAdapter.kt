package com.expostore.ui.fragment.search.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import com.expostore.databinding.SearchProductItemBinding
import com.expostore.extension.dp
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.profile.profile_edit.click

class ProductsAdapter(context:Context) :
    PagingDataAdapter<ProductModel, ProductsAdapter.ProductsViewHolder>(PRODUCTS_DIFF_UTIL) {

    var onItemClickListener: ((ProductModel) -> Unit)? = null
    var onLikeItemClickListener: ((String) -> Unit)? = null
    var onCallItemClickListener: ((String) -> Unit)? = null
    var onMessageItemClickListener: ((ProductModel) -> Unit)? = null
    var onAnotherItemClickListener:((ProductModel)->Unit)? =null
   private val queueLikes = mutableMapOf<String, (() -> Unit)>()

    fun processLike(id: String, isSuccess: Boolean) {
        if(!isSuccess) {
           queueLikes[id]?.invoke()
        }
        queueLikes.remove(id)
    }
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            SearchProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ProductsViewHolder(private val binding: SearchProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        private val adapter = ImageAdapter()
            fun bind(item: ProductModel) {
                binding.apply {
                    like.isChecked = item.isLiked
                    name.text = item.name
                    price.text = item.price.priceSeparator() + " " + "руб"
                    description.text = item.shortDescription
                    address.text = "Адрес:"+" "+item.shop.address
                }


                binding.apply {
                    viewPager.adapter = adapter
                    viewPager.setPageTransformer(MarginPageTransformer(PAGE_PADDING.dp))
                    viewPager.children.find { it is RecyclerView }?.overScrollMode =
                        RecyclerView.OVER_SCROLL_NEVER
                    adapter.onItemClickListener = {
                        onItemClickListener?.invoke(item)

                    }
                    root.click {
                        onItemClickListener?.invoke(item)
                    }
                        like.click {
                            onLikeItemClickListener?.invoke(item.id)
                        }
                        call.click {
                            onCallItemClickListener?.invoke(item.author.username)
                        }
                        write.click {
                            onMessageItemClickListener?.invoke(item)
                         }
                    another.click {
                       onAnotherItemClickListener?.invoke(item)
                    }
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