package com.expostore.ui.fragment.search.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.children
import androidx.core.view.isVisible
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
import com.expostore.ui.base.search.BasePagingAdapter
import com.expostore.ui.base.search.DrawMarkerApi
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.ProductRecyclerViewAdapter

class ProductsAdapter(context:Context,
                      override val drawMarkerApi: DrawMarkerApi<ProductModel>
) : BasePagingAdapter<SearchProductItemBinding, ProductModel>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        return ProductsViewHolder(
            SearchProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }

    inner class ProductsViewHolder( binding: SearchProductItemBinding) :
        PagingViewHolder(binding) {
        private val adapter = ImageAdapter()
       override fun bind(item: ProductModel) {
            binding.apply {
                like.isChecked = item.isLiked
                name.text = item.name
                price.text = item.price.priceSeparator() + " " + "руб"
                description.text = item.shortDescription
                address.text = "Адрес:" + " " + item.shop.address
                viewPager.adapter = adapter
                if(item.communicationType == "chatting") {
                    val params=
                        Constraints.LayoutParams( Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT)
                    params.marginStart=0
                    binding.write.layoutParams=params
                    call.isVisible=false
                }
                viewPager.setPageTransformer(MarginPageTransformer(PAGE_PADDING.dp))
                viewPager.children.find { it is RecyclerView }?.overScrollMode =
                    RecyclerView.OVER_SCROLL_NEVER
                adapter.onItemClickListener = { onItemClickListener?.invoke(item) }

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
                    onMessageItemClickListener?.invoke(item.id)
                }
                another.click {
                    onAnotherItemClickListener?.invoke(item)
                }
            }

            adapter.items = item.images.map { it.file }
        }

    }





}