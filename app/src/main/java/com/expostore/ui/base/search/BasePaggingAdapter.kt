package com.expostore.ui.base.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.expostore.data.remote.api.pojo.productcategory.Parent
import com.expostore.model.product.ProductModel


abstract class BasePagingAdapter<V:ViewBinding,T:Any>(context: Context) :
    PagingDataAdapter< T, BasePagingAdapter<V,T>.PagingViewHolder>(PagingDiffUtil()) {
       abstract val drawMarkerApi:DrawMarkerApi<T>
    var onItemClickListener: ((ProductModel) -> Unit)? = null
    var onLikeItemClickListener: ((String) -> Unit)? = null
    var onCallItemClickListener: ((String) -> Unit)? = null
    var onMessageItemClickListener: ((String) -> Unit)? = null
    var onAnotherItemClickListener:((ProductModel)->Unit)? =null

       abstract inner class PagingViewHolder(val binding: V):RecyclerView.ViewHolder(binding.root){
           abstract fun bind(item:T)
       }

      override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
            drawMarkerApi.drawMarker(it)
        }
      }

    companion object {
        const val PAGE_PADDING = 20
    }
}