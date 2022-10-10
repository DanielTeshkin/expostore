package com.expostore.ui.fragment.favorites.tabs.savedsearches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.SaveSearchItemBinding
import com.expostore.model.SaveSearchModel
import com.expostore.ui.fragment.favorites.FavoritesClickListener
import com.expostore.ui.fragment.search.filter.models.FilterModel

class TabSavedSearchAdapter(
    private val list: MutableList<SaveSearchModel>,
    private val onClick: OnClickSaveSearch,

): RecyclerView.Adapter<TabSavedSearchAdapter.TabSearchHolder>() {

    inner class TabSearchHolder(val binding:SaveSearchItemBinding):RecyclerView.ViewHolder(binding.root) {
                  fun bind(model: SaveSearchModel){
                      binding.apply {
                          model.body_params.category?:"Нет категории"
                          textView4.text=model.params?.q
                          textView5.text="Cоздан:"+model.date_create
                          cvSaveSearch.setOnClickListener { onClick.onClickSaveSearch(model) }
                          ibDetailProductLike.setOnClickListener { onClick.onClickLike(model.id) }
                      }


                  }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabSearchHolder {
               return TabSearchHolder(SaveSearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TabSearchHolder, position: Int) {
                  holder.bind(list[position])
    }

    override fun getItemCount(): Int =list.size

}