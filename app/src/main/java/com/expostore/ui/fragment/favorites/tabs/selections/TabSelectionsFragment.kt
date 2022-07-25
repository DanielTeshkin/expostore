package com.expostore.ui.fragment.favorites.tabs.selections

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.TabSelectionsFragmentBinding

import com.expostore.model.category.SelectionModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.favorites.FavoritesClickListener

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabSelectionsFragment(private val installClickListener: FavoritesClickListener) :
    BaseFragment<TabSelectionsFragmentBinding>(TabSelectionsFragmentBinding::inflate) {
    private val viewModel: TabSelectionsViewModel by viewModels()
  private lateinit var onClick: OnClickCategory

  override fun onStart() {
        super.onStart()
        subscribe()
        init()
    }

   private fun subscribe(){
       val show:Show<List<SelectionModel>> = { showUserCategory(it as MutableList<SelectionModel>)}
       viewModel.apply {
           loadList()
           subscribe(state){handleState(it,show)}
           subscribe(navigation){navigateSafety(it)}
       }
    }

    private fun init(){
        onClick=object :OnClickCategory {
            override fun onClickSelection(model: SelectionModel,flag:String) {
          Log.i("dkdkkd","ddd")
            }

            override fun onClickLike(id: String) {
                viewModel.delete(id)
            }

        }
    }


    private fun showUserCategory(list: MutableList<SelectionModel>){
        binding.rvUser.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=TabSelectionAdapter(list,onClick,installClickListener)
        }
    }


}