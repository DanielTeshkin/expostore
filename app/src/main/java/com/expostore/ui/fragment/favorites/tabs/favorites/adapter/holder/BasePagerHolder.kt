package com.expostore.ui.fragment.favorites.tabs.favorites.adapter.holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.databinding.DetailProductImageItemBinding
import com.expostore.model.product.ProductModel
import com.expostore.utils.OnClickFavoriteProductListener

abstract class BasePagerHolder(val binding: DetailProductImageItemBinding,val context: Context,
                               val onClick: OnClickFavoriteProductListener, val id:String) :RecyclerView.ViewHolder(binding.root){
     fun bind(url:String){
         binding.apply {
             Glide.with(context).load(url).centerCrop().into(ivDetailProduct)
             ibDetailProductLike.setOnClickListener {
                 onClick.onClickLike(id)
             }
         }
         stateLike()

     }
    abstract fun stateLike()

}