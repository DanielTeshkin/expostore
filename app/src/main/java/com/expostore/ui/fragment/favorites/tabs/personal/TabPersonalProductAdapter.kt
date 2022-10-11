package com.expostore.ui.fragment.favorites.tabs.personal

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.DetailProductItemBinding
import com.expostore.databinding.PersonalProductItemBinding
import com.expostore.databinding.PersonalProductsFragmentBinding
import com.expostore.databinding.SaveSelectionBinding

import com.expostore.model.category.SelectionModel
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.profile.profile_edit.click


class TabPersonalProductRecyclerViewAdapter(
    private var products: MutableList<ProductModel>
    ) : RecyclerView.Adapter<TabPersonalProductRecyclerViewAdapter.FavoritesProductViewHolder>() {

   var onItemClick:((ProductModel)->Unit)?=null
    var onDeleteClick:((String) -> Unit)?=null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesProductViewHolder {
        val v = PersonalProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesProductViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products!!.size

    inner class FavoritesProductViewHolder(val binding:PersonalProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind( product: ProductModel){

            binding.price.text=product.price.priceSeparator() +" " +"рублей"
            val list=ArrayList<String>()

            product.images.map { list.add(it.file) }
            binding.name.text = product.name
            binding.note.text=product.elected.notes
            binding.viewPager.apply {
                val tabProductPagerAdapter= ImageAdapter()
                tabProductPagerAdapter.onItemClickListener={onItemClick?.invoke(product)}
                tabProductPagerAdapter.items=list
                adapter=tabProductPagerAdapter
            }
            binding.delete.click {
                onDeleteClick?.invoke(product.id)
               // removeAt(product)
            }


        }

    }

    override fun onBindViewHolder(holder: FavoritesProductViewHolder, position: Int) {
        holder.bind(products[position])

    }
    fun removeAt(model: ProductModel) {
        val index=products.indexOf(model)
        products.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,itemCount);
    }


}


