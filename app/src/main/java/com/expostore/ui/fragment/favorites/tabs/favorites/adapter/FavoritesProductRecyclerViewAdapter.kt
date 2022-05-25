package com.expostore.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.expostore.R
import com.expostore.api.pojo.getcategory.ImageResponseData
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.databinding.DetailProductImageItemBinding
import com.expostore.databinding.DetailProductItemBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.favorites.tabs.favorites.adapter.TabProductPagerAdapter
import com.expostore.ui.fragment.profile.profile_edit.click
import kotlinx.android.synthetic.main.detail_product_item.view.*

class FavoritesProductRecyclerViewAdapter(private val products: List<FavoriteProduct>,val onClickListener: OnClickFavoriteProductListener,val context: Context) : RecyclerView.Adapter<FavoritesProductRecyclerViewAdapter.FavoritesProductViewHolder>() {


    var onClick: OnClickRecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesProductViewHolder {
        val v =DetailProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class FavoritesProductViewHolder(val binding: DetailProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item:FavoriteProduct){
            val product= item.product
            binding.tvDetailProductPrice.text=product.price +" " +"рублей"
            val list=ArrayList<String>()

           product.images.map { list.add(it.file) }
            binding.tvDetailProductName.text = product.name
            binding.rvDetailProductImages.apply {
               val tabProductPagerAdapter=ImageAdapter()
                tabProductPagerAdapter.items=list
                tabProductPagerAdapter.onItemClickListener= { onClickListener.onClickProduct(product) }
                adapter=tabProductPagerAdapter
            }

            if(item.notes!=null){
                binding.tvDetailProductNote.text=item.notes
            }
            else{
                binding.apply {
                    tvDetailProductNoteTitle.text="Описание"
                    tvDetailProductNote.text=product.shortDescription
                }
            }
            binding.like.click {
                onClickListener.onClickLike(product.id)
            }
        }

    }

    override fun onBindViewHolder(holder: FavoritesProductViewHolder, position: Int) {
                        holder.bind(products[position])

    }

}

