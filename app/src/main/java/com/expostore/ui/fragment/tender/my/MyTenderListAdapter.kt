package com.expostore.ui.fragment.tender.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.MyTenderItemBinding
import com.expostore.model.tender.TenderModel
import com.expostore.model.tender.priceRange
import com.expostore.ui.base.ImageAdapter

class MyTenderListAdapter(private val products: List<TenderModel>):
    RecyclerView.Adapter<MyTenderListAdapter.MyTenderHolder>() {
    var  onClickMyTender:OnClickMyTender?=null

    inner class MyTenderHolder(val binding: MyTenderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TenderModel) {
            binding.apply {
                tvDetailProductPrice.text = model.priceRange()
                tvDetailProductName.text = model.title
                address.text = model.description
                val adapter= ImageAdapter()
                adapter.onItemClickListener={onClickMyTender?.onClickTender(model)}
                adapter.items=model.images?.map { it.file }?: listOf()

                rvDetailProductImages.adapter=adapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTenderHolder {

        return MyTenderHolder(
            MyTenderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyTenderHolder, position: Int) {
        holder.bind(products[position])

    }

    override fun getItemCount(): Int = products.size

}