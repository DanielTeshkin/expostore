package com.expostore.ui.fragment.favorites.tabs.selections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabSelectionsFragmentBinding
import com.expostore.model.category.CategoryModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import com.expostore.utils.OnClickSelectionCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabSelectionsFragment :
    BaseFragment<TabSelectionsFragmentBinding>(TabSelectionsFragmentBinding::inflate) {
    private val viewModel: TabSelectionsViewModel by viewModels()
  private lateinit var onClick:OnClickSelectionCategory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        subscribe()
        init()
    }

   private fun subscribe(){
       viewModel.apply {
           loadList()
           subscribe(state){handleState(it)}
           subscribe(navigation){navigateSafety(it)}
       }
    }

    private fun init(){
        onClick=object :OnClickSelectionCategory{
            override fun onClickSelection() {
               viewModel.navigate()
            }

            override fun onClickLike(id: String) {
                viewModel.delete(id)
            }

        }
    }


    private fun handleState(state: ResponseState<List<CategoryModel>>) {
        when(state) {
            is ResponseState.Success -> showUserCategory(state.item as MutableList<CategoryModel>)
        }
    }
    private fun showUserCategory(list: MutableList<CategoryModel>){
        binding.rvUser.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=TabSelectionAdapter(list,onClick)
        }
    }


}