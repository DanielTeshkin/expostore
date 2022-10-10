package com.expostore.ui.fragment.favorites.tabs.savedsearches

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabSavedSearchesFragmentBinding
import com.expostore.model.SaveSearchModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.search.filter.models.FilterModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabSavedSearchesFragment( ) :
    BaseFragment<TabSavedSearchesFragmentBinding>(TabSavedSearchesFragmentBinding::inflate) {

    private val tabSavedSearchesViewModel:TabSavedSearchesViewModel by viewModels()
     private lateinit var onClickSaveSearch: OnClickSaveSearch

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showList:Show<List<SaveSearchModel>> ={showList(it)}
        tabSavedSearchesViewModel.apply {
            subscribe(searchList){handleState(it,showList)}
            subscribe(navigation){navigateSafety(it)}
        }
    }
    override fun onStart() {
        super.onStart()
        onClickSaveSearch = object : OnClickSaveSearch {
            override fun onClickSaveSearch(model:SaveSearchModel) {
                tabSavedSearchesViewModel.navigate(model)
            }
            override fun onClickLike(id: String) {
                tabSavedSearchesViewModel.deleteSaveSearch(id) } }
        tabSavedSearchesViewModel.loadList()
    }
    private fun showList(item: List<SaveSearchModel>) {
        binding.progressBar12.visibility=View.GONE
        binding.rvSearches.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=TabSavedSearchAdapter(item as MutableList<SaveSearchModel>,onClickSaveSearch)
        }

    }


}