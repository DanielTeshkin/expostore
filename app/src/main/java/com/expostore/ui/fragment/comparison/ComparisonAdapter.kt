package com.expostore.ui.fragment.comparison

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.CharacteristicComparisonItemBinding
import com.expostore.model.ComparisonModel

class ComparisonAdapter :RecyclerView.Adapter<ComparisonAdapter.ComparisonHolder>() {

    private val models = mutableListOf<ComparisonModel>()
    inner class ComparisonHolder(val binding:CharacteristicComparisonItemBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun bind(model: ComparisonModel){
                binding.apply {
                    type.text=model.name
                     meanFirst.text=model.firstProductMean
                    meanSecond.text=model.secondProductMean
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComparisonHolder {
      return  ComparisonHolder(
            CharacteristicComparisonItemBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ComparisonHolder, position: Int) {
       holder.bind(models[position])
    }

    override fun getItemCount(): Int =models.size

    fun addModels(list:List<ComparisonModel>){
        models.clear()
        models.addAll(list)
        Log.i("my",list.size.toString())
        notifyDataSetChanged()
    }
}