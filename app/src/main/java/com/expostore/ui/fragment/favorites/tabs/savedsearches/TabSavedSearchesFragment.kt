package com.expostore.ui.fragment.favorites.tabs.savedsearches

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabSavedSearchesFragmentBinding
import com.expostore.model.SaveSearchModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import com.expostore.utils.OnClickSaveSearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabSavedSearchesFragment :
    BaseFragment<TabSavedSearchesFragmentBinding>(TabSavedSearchesFragmentBinding::inflate) {

    private val tabSavedSearchesViewModel:TabSavedSearchesViewModel by viewModels()
     private lateinit var onClickSaveSearch: OnClickSaveSearch

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabSavedSearchesViewModel.apply {
            subscribe(searchList){handleState(it)}
            subscribe(navigation){navigateSafety(it)}
        }
    }
    override fun onStart() {
        super.onStart()
        onClickSaveSearch = object :OnClickSaveSearch{
            override fun onClickSaveSearch() {
                tabSavedSearchesViewModel.navigate()
            }
            override fun onClickLike(id: String) {
                tabSavedSearchesViewModel.deleteSaveSearch(id) }
        }
        tabSavedSearchesViewModel.loadList()
    }

    private fun handleState(state: ResponseState<List<SaveSearchModel>>) {
        when(state){
            is ResponseState.Success -> showList(state.item)
        }
    }
    private fun showList(item: List<SaveSearchModel>) {
        binding.rvSearches.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=TabSavedSearchAdapter(item as MutableList<SaveSearchModel>,onClickSaveSearch)
        }

    }


}