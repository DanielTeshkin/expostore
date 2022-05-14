package com.expostore.utils

import android.annotation.SuppressLint
import android.content.Context
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
import com.expostore.ui.fragment.favorites.tabs.favorites.adapter.TabProductPagerAdapter
import kotlinx.android.synthetic.main.detail_product_item.view.*

class FavoritesProductRecyclerViewAdapter(private val products: List<FavoriteProduct>,val onClickListener: OnClickFavoriteProductListener,val context: Context) : RecyclerView.Adapter<FavoritesProductRecyclerViewAdapter.FavoritesProductViewHolder>() {


    var onClick: OnClickRecyclerViewListener? = null
    lateinit var tabProductPagerAdapter: TabProductPagerAdapter

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
            binding.tvDetailProductPrice.text=item.product.price +" " +"рублей"
            val list=ArrayList<String>()
            item.product.images.map { list.add(it.file) }
            binding.tvDetailProductName.text = item.product.name
            binding.rvDetailProductImages.apply {
                tabProductPagerAdapter=TabProductPagerAdapter(list,onClickListener,item.product.id,context)
                layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                adapter=tabProductPagerAdapter

            }

            if(item.notes!=null){
                binding.tvDetailProductNote.text=item.notes
            }
            else{
                binding.apply {
                    tvDetailProductNoteTitle.text="Описание"
                    tvDetailProductNote.text=item.product.shortDescription
                }
            }
        }

    }

    override fun onBindViewHolder(holder: FavoritesProductViewHolder, position: Int) {
                        holder.bind(products[position])
        holder.binding.productOpen.setOnClickListener {
            onClickListener.onClickProduct(products[position].product)
        }
    }

    fun update(state:Boolean){
        tabProductPagerAdapter.updateState(state)
    }
}

