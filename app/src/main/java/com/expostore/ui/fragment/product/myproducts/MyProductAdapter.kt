package com.expostore.ui.fragment.product.myproducts

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.MyProductItemBinding
import com.expostore.databinding.PublicProductFragmentBinding

import com.expostore.model.product.ProductModel
import com.expostore.ui.base.ImageAdapter


class MyProductAdapter(private val products: List<ProductModel>,private  val onClickMyProduct: OnClickMyProduct):RecyclerView.Adapter<MyProductAdapter.MyProductHolder>() {

    inner class MyProductHolder(val binding: MyProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ProductModel) {
            binding.apply {
                tvDetailProductPrice.text = model.price + " " + "рублей"
                tvDetailProductName.text = model.name
                datePublic.text = "Дата окончания публикации:" + " " +model.endDateOfPublication
                address.text = "Адрес:" + " " + model.shop.address
                val adapter=ImageAdapter()

                adapter.items=model.images.map { it.file }
                adapter.onItemClickListener={onClickMyProduct.click()}
                rvDetailProductImages.adapter=adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductHolder {

        return MyProductHolder(
            MyProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyProductHolder, position: Int) {
        holder.bind(products[position])

    }

    override fun getItemCount(): Int = products.size

}