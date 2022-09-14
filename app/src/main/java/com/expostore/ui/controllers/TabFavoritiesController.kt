package com.expostore.ui.controllers

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.Product
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.utils.FavoritesProductRecyclerViewAdapter

class TabFavoritesController(private val
                             binding: TabFavoritesFragmentBinding,context: Context)
    :BaseProductController(context) {
    private val productList= mutableListOf<FavoriteProduct>()
     private val myAdapter :FavoritesProductRecyclerViewAdapter by lazy{
         FavoritesProductRecyclerViewAdapter(products = productList) }
    fun showFavorites(item: List<FavoriteProduct>) {
        productList.addAll(item)
        binding.apply {
            rvFavorites.apply {
                layoutManager = manager
                adapter=myAdapter
                progressBar4.visibility = View.GONE
            }
        }
    }

    override fun setEvent(onClick:OnClickListener) {myAdapter.onClickListener=onClick }
}