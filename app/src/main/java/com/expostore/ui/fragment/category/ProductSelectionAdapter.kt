package com.expostore.ui.fragment.category


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.SearchProductItemBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.profile.profile_edit.click




class ProductSelectionAdapter(private val products:MutableList<ProductModel>) :RecyclerView.Adapter<ProductSelectionAdapter.SelectionProductViewHolder>() {
   var onClick: OnClickListener? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionProductViewHolder {
        val v = SearchProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectionProductViewHolder(v)
    }



    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = products.size

    inner class SelectionProductViewHolder(val binding: SearchProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            val  adapter=ImageAdapter()
            adapter.onItemClickListener={onClick?.onClickProduct(item)}
            adapter.items=item.images.map { it.file }
            binding.apply {
                name.text=item.name
                price.text=item.price
                description.text=item.shortDescription
                address.text= "Адрес"+" "+ item.shop.address
                viewPager.adapter=adapter
                like.isChecked=item.isLiked
                like.click { onClick?.onClickLike(item.id) }
                if(item.communicationType == "chatting") call.isVisible=false
                call.click { onClick?.onClickCall(item.author.username) }
                root.click { onClick?.onClickProduct(item) }
                write.click { onClick?.onClickMessage(item) }
                another.click { onClick?.onClickAnother(model = item) }

            }

        }
    }
    override fun onBindViewHolder(holder: SelectionProductViewHolder, position: Int) {
        holder.bind(products[position])

    }

}