package com.expostore.ui.fragment.product.myproducts.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.databinding.MyProductItemBinding

import com.expostore.model.product.ProductModel
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.product.myproducts.OnClickMyProduct
import com.expostore.ui.fragment.profile.profile_edit.click


class MyProductAdapter
    (
    private val products: List<ProductModel>,

   private val status: String
):RecyclerView.Adapter<MyProductAdapter.MyProductHolder>() {
    var onClickMyProduct: OnClickMyProduct?=null

    inner class MyProductHolder(val binding: MyProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: ProductModel) {
            binding.apply {
                tvDetailProductPrice.text = model.price + " " + "рублей"
                tvDetailProductName.text = model.name
                when(status){
                    "draft"->{
                        datePublic.setTextColor(Color.RED)
                        datePublic.text="Черновик"
                    }
                    "archive"->{
                        datePublic.setTextColor(Color.RED)
                        datePublic.text="Заблокирован"
                    }
                    else->  datePublic.text = "Дата окончания публикации: ${model.endDateOfPublication}"
                }


                address.text = "Адрес:" + " " + model.shop.address
                val adapter=ImageAdapter()
                adapter.items=model.images.map { it.file }
                adapter.onItemClickListener={onClickMyProduct?.click(model)}
                rvDetailProductImages.adapter=adapter
                llDetailProductInfo.click {
                    onClickMyProduct?.click(model)
                }
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