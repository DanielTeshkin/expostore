package com.expostore.ui.fragment.favorites.tabs.favorites.adapter.holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.databinding.DetailProductImageItemBinding
import com.expostore.model.product.ProductModel
import com.expostore.utils.OnClickFavoriteProductListener

class PagerLikeHolder(binding: DetailProductImageItemBinding, context:Context,
                       onClick: OnClickFavoriteProductListener,  id:String):BasePagerHolder(binding,context,onClick, id) {
    override fun stateLike() {
        binding.ibDetailProductLike.isChecked=true
    }

}