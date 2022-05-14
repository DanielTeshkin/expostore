package com.expostore.ui.fragment.favorites.tabs.favorites.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.R
import com.expostore.databinding.DetailProductImageItemBinding
import com.expostore.extension.loadBanner
import com.expostore.utils.OnClickFavoriteProductListener



class TabProductPagerAdapter(private val images:MutableList<String>,val onClickListener: OnClickFavoriteProductListener, val id:String,val context: Context) : RecyclerView.Adapter<TabProductPagerAdapter.PagerVH>() {

    private var state=true

    inner class PagerVH(val binding: DetailProductImageItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
     fun bind(url:String){
            binding.apply {
             Glide.with(context).load(url).centerCrop().into(ivDetailProduct)
                when(state){
                 true ->ibDetailProductLike.isChecked=true
                 false-> ibDetailProductLike.isChecked=false
                }
          }
            binding.apply {
             ibDetailProductLike.setOnClickListener {
                 onClickListener.onClickLike(id)
             }
         }

      }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
      return PagerVH(DetailProductImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
       holder.bind(images[position])
    }

    override fun getItemCount(): Int {
         return images.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateState(new_state:Boolean){
        state=new_state
        notifyDataSetChanged()
    }



}
