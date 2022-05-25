package com.expostore.ui.fragment.favorites.tabs.favorites.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.DetailProductImageItemBinding
import com.expostore.model.product.ProductModel
import com.expostore.utils.OnClickFavoriteProductListener

class PagerHolderChooser(val state: Boolean) {
    lateinit var holder: BasePagerHolder
    fun createHolder(parent: ViewGroup,  context: Context, onClick: OnClickFavoriteProductListener,
                     id:String):BasePagerHolder {
       val v= DetailProductImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       holder= when(state){
            true-> PagerLikeHolder(v,context,onClick,id)
            false-> PagerUnLikeHolder(v,context,onClick,id)
        }

        return holder
    }

}