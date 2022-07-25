package com.expostore.ui.fragment.search.filter.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.fragment.search.filter.adapter.holders.BaseFilterHolder
import com.expostore.ui.fragment.search.filter.adapter.holders.InputViewHolder
import com.expostore.ui.fragment.search.filter.adapter.holders.TypeFilterHolder
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState

class FilterRecyclerAdapter(val context: Context, private val filterState: FilterState, private val filter: String): RecyclerView.Adapter<BaseFilterHolder>() {


     val selectList= mutableListOf<String>()
    private val typeHolder=TypeFilterHolder(context,filterState,filter)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFilterHolder {
        return typeHolder.createHolder(viewType, parent)
    }
     private val category = mutableListOf<CategoryCharacteristicModel>()

    override fun getItemViewType(position: Int): Int = typeHolder.getType(category[position].type)

    override fun getItemCount(): Int =category.size
    override fun onBindViewHolder(holder: BaseFilterHolder, position: Int) {
        when(holder){
            is InputViewHolder -> holder.bind(category[position])
            else->holder.bind(category[position])
        }
    }

fun removeSelect(id:String){
    selectList.remove(id)
}
    fun addSelect(id: String){
        selectList.add(id)
    }

 fun addElement(list:List<CategoryCharacteristicModel>){
     category.addAll(list)
 }

}
