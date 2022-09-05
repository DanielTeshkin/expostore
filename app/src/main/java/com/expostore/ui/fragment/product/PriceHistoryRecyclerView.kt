package com.expostore.ui.fragment.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.PriceHistoryFragmentBinding
import com.expostore.databinding.PriceItemBinding
import com.expostore.model.product.PriceHistoryModel

class PriceHistoryRecyclerView(private val history:List<PriceHistoryModel>): RecyclerView.Adapter<PriceHistoryRecyclerView.PriceViewHolder>() {
  inner  class PriceViewHolder(val binding: PriceItemBinding):RecyclerView.ViewHolder(binding.root){
                  fun bind(model: PriceHistoryModel){
                      binding.apply {
                          priceMean.text=model.price
                          date.text=model.date_create
                      }
                  }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
       return  PriceViewHolder(PriceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        holder.bind(history[position])
    }

    override fun getItemCount(): Int=history.size
}