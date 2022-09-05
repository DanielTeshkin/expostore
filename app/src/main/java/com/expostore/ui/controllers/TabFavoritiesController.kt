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

class TabFavoritesController(private val binding: TabFavoritesFragmentBinding, private val context:Context) {
    val products= mutableListOf<FavoriteProduct>()
     private val myAdapter :FavoritesProductRecyclerViewAdapter by lazy{ FavoritesProductRecyclerViewAdapter(products = products) }

    fun showFavorites(item: List<FavoriteProduct>) {
        products.addAll(item)
        binding.apply {
            rvFavorites.apply {
                layoutManager = LinearLayoutManager(context)
                adapter=myAdapter
                progressBar4.visibility = View.GONE
            }
        }
    }


    fun setEvent(onClick:OnClickListener) {myAdapter.onClickListener=onClick }
}