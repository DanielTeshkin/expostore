package com.expostore.ui.favorites.tabs.favorites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.databinding.FavoritesFragmentBinding
import com.expostore.databinding.TabFavoritesFragmentBinding
import com.expostore.ui.favorites.FavoritesTabsViewPagerAdapter
import com.expostore.ui.favorites.FavoritesViewModel
import com.expostore.utils.*
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TabFavoritesFragment : Fragment() {

    private lateinit var binding: TabFavoritesFragmentBinding
    private lateinit var tabFavoritesViewModel: TabFavoritesViewModel
    private lateinit var mAdapter: FavoritesProductRecyclerViewAdapter
    lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_favorites_fragment, container, false)
        tabFavoritesViewModel = ViewModelProvider(this).get(TabFavoritesViewModel::class.java)
        binding.tabFavoritesVM = tabFavoritesViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val token = (context as MainActivity).sharedPreferences.getString("token", "")

        if (token.isNullOrEmpty()){
            navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.action_mainFragment_to_openFragment)
        }

        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.getFavoritesList("Bearer $token").enqueue(object : Callback<ArrayList<GetFavoritesListResponseData>> {
            override fun onResponse(call: Call<ArrayList<GetFavoritesListResponseData>>, response: Response<ArrayList<GetFavoritesListResponseData>>) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        mAdapter = FavoritesProductRecyclerViewAdapter(response.body(), requireContext())
                        binding.rvFavorites.apply {
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = mAdapter
                        }
                        mAdapter.onClick = onLikeClick()
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<GetFavoritesListResponseData>>, t: Throwable) {}
        })

    }

    private fun onLikeClick() : OnClickRecyclerViewListener {
        return object : OnClickRecyclerViewListener {

            override fun onLikeClick(like: Boolean, id: String?) {

                val token = (context as MainActivity).sharedPreferences.getString("token", "")

                if (token.isNullOrEmpty()){
                    //Todo Подумать над нулевым токеном (возможно кидать на экран авторизации)
                    //navController = Navigation.findNavController(binding.root)
                    //navController.navigate(R.id.action_mainFragment_to_openFragment)
                }

                val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

                if (!id.isNullOrEmpty())
                    serverApi.selectFavorite("Bearer $token", id).enqueue(object : Callback<SelectFavoriteResponseData> {
                        override fun onResponse(call: Call<SelectFavoriteResponseData>, response: Response<SelectFavoriteResponseData>) {
                            try {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Toвар добавлен в избранное",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    if (response.errorBody() != null) {
                                        val jObjError = JSONObject(response.errorBody()!!.string())
                                        val message = jObjError.getString("detail")
                                        if (message.isNotEmpty())
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                            catch (e: Exception){
                                Log.d("exception", e.toString())
                            }
                        }

                        override fun onFailure(call: Call<SelectFavoriteResponseData>, t: Throwable) {
                            Toast.makeText(context, (context as MainActivity).getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
                        }
                    })
            }

            override fun onDetailCategoryProductItemClick(id: String?) {
                TODO("Not yet implemented")
            }

        }
    }

}