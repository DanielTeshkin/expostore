package com.expostore.ui.fragment.tender.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import com.expostore.databinding.SearchProductItemBinding
import com.expostore.databinding.TenderListItemBinding
import com.expostore.extension.dp
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.chats.dialog.adapter.DiffUtilDialog
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.tender.utils.OnClickTender
class TenderAdapter :
    PagingDataAdapter<TenderModel, TenderAdapter.ProductsViewHolder>(PRODUCTS_DIFF_UTIL) {

    var onItemClickListener: ((TenderModel) -> Unit)? = null
    var onLikeItemClickListener: ((String) -> Unit)? = null
    var onCallItemClickListener: ((String) -> Unit)? = null
    var onMessageItemClickListener:((String)->Unit)? = null
    var onAnotherItemClickListener:((TenderModel)->Unit)? =null
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
        holder.bind(getItem(position) ?: TenderModel())
    }

    inner class ProductsViewHolder(private val binding:  SearchProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = ImageAdapter()

        init {
            binding.apply {
                viewPager.adapter = adapter
                viewPager.setPageTransformer(MarginPageTransformer(PAGE_PADDING.dp))
                viewPager.children.find { it is RecyclerView }?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            }

        }

        fun bind(item: TenderModel) {
            binding.apply {

                name.text = item.title
                price.text = item.price
               description.text = item.description
                address.text = item.location
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
                    onMessageItemClickListener?.invoke(item.id)
                }
                another.click {
                    onAnotherItemClickListener?.invoke(item)
                }
            }
            adapter.items = item.images?.map { it.file }?: listOf()
        }
    }

    fun update(){
        notifyDataSetChanged()
    }

    companion object {
        private const val PAGE_PADDING = 20

        private val PRODUCTS_DIFF_UTIL = object : DiffUtil.ItemCallback<TenderModel>() {
            override fun areItemsTheSame(oldItem: TenderModel, newItem: TenderModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: TenderModel, newItem: TenderModel): Boolean =
                oldItem == newItem
        }
    }

}