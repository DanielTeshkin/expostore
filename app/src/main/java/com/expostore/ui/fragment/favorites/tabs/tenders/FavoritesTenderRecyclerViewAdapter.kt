package com.expostore.ui.fragment.favorites.tabs.tenders

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.DetailProductItemBinding
import com.expostore.databinding.SearchProductItemBinding
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.favorites.FavoritesClickListener
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickFavoriteProductListener
import com.expostore.utils.OnClickRecyclerViewListener
import kotlinx.android.synthetic.main.detail_product_item.view.*

class FavoritesTenderRecyclerViewAdapter(
    private val tenders: MutableList<FavoriteTender>,


) : RecyclerView.Adapter<FavoritesTenderRecyclerViewAdapter.FavoritesTenderViewHolder>() {

    var onCallItemClickListener: ((String) -> Unit)? = null
    var onMessageItemClickListener: ((String) -> Unit)? = null
    var onAnotherClickListener: ((TenderModel)->Unit)?=null
    var onClickLike: ((String) -> Unit)? = null
    var onItemClickListener:((TenderModel)->Unit)?=null


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
            val tender= item.tender
            val list=ArrayList<String>()
            binding.price.text=tender.price
            tender.images?.map { list.add(it.file) }
            binding.name.text = tender.title
            binding.viewPager.apply {
                val tabProductPagerAdapter= ImageAdapter()
                tabProductPagerAdapter.items=list
                tabProductPagerAdapter.onItemClickListener= { onItemClickListener?.invoke(tender)}
                adapter=tabProductPagerAdapter
            }
            if(tender.communicationType == "chatting"){
                val params=
                    Constraints.LayoutParams( Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT)
                params.marginStart=0
                binding.write.layoutParams=params
                binding.call.isVisible=false
            }

            if(item.notes!=null){
                binding.note.text=item.notes
            }
            else{
                binding.note.text="Нет заметки"
            }
            binding.write.click { onMessageItemClickListener?.invoke(tender.id)}
            binding.call.click { onCallItemClickListener?.invoke(tender.author.username) }
            binding.another.click{onAnotherClickListener?.invoke(tender)}

            binding.like.click {
            onClickLike?.invoke(tender.id)

            }
        }

    }

    override fun onBindViewHolder(holder: FavoritesTenderViewHolder, position: Int) {
        holder.bind(tenders[position],position)

    }


}
