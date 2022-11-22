package com.expostore.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.DetailProductItemBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.ui.fragment.category.OnClickListeners
import com.expostore.ui.fragment.favorites.FavoritesClickListener
import com.expostore.ui.fragment.product.addproduct.stroke
import com.expostore.ui.fragment.profile.profile_edit.click
import kotlinx.android.synthetic.main.detail_product_item.view.*


class FavoritesProductRecyclerViewAdapter(
    private val products: MutableList<FavoriteProduct>,

    ) : RecyclerView.Adapter<FavoritesProductRecyclerViewAdapter.FavoritesProductViewHolder>() {

    var onClickListener: OnClickListeners<ProductModel>?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesProductViewHolder {
        val v =DetailProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size
    fun update()=notifyDataSetChanged()

    inner class FavoritesProductViewHolder(val binding: DetailProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item:FavoriteProduct,index: Int){
            val product= item.product
            binding.price.text=product.price.priceSeparator() +" " +"руб"
            val list=ArrayList<String>()
            product.images.map { list.add(it.file) }
            binding.name.text = product.name
            if(product.communicationType == "chatting") {
                val params=
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                params.marginStart=0
                binding.write.layoutParams=params
                binding.call.isVisible=false
            }
            binding.viewPager.apply {
                val tabProductPagerAdapter=ImageAdapter()
                tabProductPagerAdapter.items=list
                tabProductPagerAdapter.onItemClickListener={onClickListener?.onClickItem(product)}
                adapter=tabProductPagerAdapter
            }
            binding.call.click {onClickListener?.onClickCall(product.author.username ) }
            binding.write.click { onClickListener?.onClickMessage(product.id) }
            binding.another.click {onClickListener?.onClickAnother(product)}
            binding.like.click { onClickListener?.onClickLike(product.id) }
            binding.root.click { onClickListener?.onClickItem(product) }
            if(item.notes!=null){
                binding.note.text=item.notes
            }
            else{
                binding.note.text="Нет заметки"
            }

        }

    }

    override fun onBindViewHolder(holder: FavoritesProductViewHolder, position: Int) {
        holder.bind(products[position],position)

    }


}

