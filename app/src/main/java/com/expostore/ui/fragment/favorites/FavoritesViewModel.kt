package com.expostore.ui.fragment.favorites

import androidx.lifecycle.ViewModel
import com.expostore.ui.base.BaseViewModel

class FavoritesViewModel : BaseViewModel() {
    override fun onStart() {
        TODO("Not yet implemented")
    }

    fun navigateToSelection(){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToDetailCategoryFragment2())
    }
    fun navigateToSearch(typeSearch: String?) {
        if (typeSearch=="product")
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToSearchFragment())
        else navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToTenderListFragment())
    }
    fun navigateToProduct(){
        navigationTo(FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment2())
    }

}