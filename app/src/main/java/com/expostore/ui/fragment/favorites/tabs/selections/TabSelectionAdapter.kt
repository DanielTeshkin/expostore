package com.expostore.ui.fragment.favorites.tabs.selections

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.SaveSelectionBinding
import com.expostore.model.category.CategoryModel
import com.expostore.utils.OnClickSelectionCategory

class TabSelectionAdapter(private val list: MutableList<CategoryModel>, val onClickSelectionCategory: OnClickSelectionCategory): RecyclerView.Adapter<TabSelectionAdapter.TabSelectionHolder>() {
    inner class TabSelectionHolder(val binding:SaveSelectionBinding):RecyclerView.ViewHolder(binding.root) {
                           @SuppressLint("SetTextI18n")
                           fun bind(model: CategoryModel){
                               binding.apply {
                                   nameCategory.text = model.name

                                   binding.textView5.text ="Cоздан:"+model.date_create
                                   ibDetailProductLike.setOnClickListener{
                                       onClickSelectionCategory.onClickLike(model.id)

                                   }
                                   cvDetailProduct.setOnClickListener{
                                       onClickSelectionCategory.onClickSelection()
                                   }
1
                               }

                           }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabSelectionHolder {
             return TabSelectionHolder(SaveSelectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TabSelectionHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount(): Int=list.size
}