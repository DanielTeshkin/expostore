package com.expostore.ui.fragment.favorites.tabs.tenders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.DetailProductItemBinding
import com.expostore.databinding.SearchProductItemBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.favorites.FavoritesClickListener
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickFavoriteProductListener
import com.expostore.utils.OnClickRecyclerViewListener
import kotlinx.android.synthetic.main.detail_product_item.view.*

class FavoritesTenderRecyclerViewAdapter(private val tenders: MutableList<FavoriteTender>, val context: Context) : RecyclerView.Adapter<FavoritesTenderRecyclerViewAdapter.FavoritesTenderViewHolder>() {


    var onClick: OnClickRecyclerViewListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesTenderViewHolder {
        val v = DetailProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesTenderViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = tenders!!.size

    inner class FavoritesTenderViewHolder(val binding:DetailProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: FavoriteTender, index: Int){
            val product= item.tender
            binding.price.text="От"+" "+product.priceFrom +" "+"до" +" "+product.priceUpTo+" " +"рублей"
            val list=ArrayList<String>()

            product.images?.map { list.add(it.file) }
            binding.name.text = product.title
            binding.viewPager.apply {
                val tabProductPagerAdapter= ImageAdapter()
                tabProductPagerAdapter.items=list
                tabProductPagerAdapter.onItemClickListener= { }
                adapter=tabProductPagerAdapter
            }

            if(item.notes!=null){
                binding.note.text=item.notes
            }
            else{
                binding.note.text="Нет заметки"
            }


            binding.like.click {
               // onClickListener.onClickLikeTender(product.id)

            }
        }

    }

    override fun onBindViewHolder(holder: FavoritesTenderViewHolder, position: Int) {
        holder.bind(tenders[position],position)

    }


}
