package com.expostore.ui.shop

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.api.pojo.getshop.GetShopResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.ui.product.ProductViewModel
import com.expostore.utils.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ShopFragment : Fragment() {

    private lateinit var binding: ShopFragmentBinding
    private lateinit var shopViewModel: ShopViewModel
    private var shopId: String? = null
    lateinit var mAdapter: DetailCategoryRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.shop_fragment, container, false)
        shopViewModel = ViewModelProvider(this)[ShopViewModel::class.java]
        binding.shopVM = shopViewModel
        shopId = arguments?.getString("shopId")
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = (context as MainActivity).sharedPreferences.getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        shopId?.let {
            serverApi.getShop("Bearer $token", it).enqueue(object :
                Callback<GetShopResponseData> {
                override fun onResponse(call: Call<GetShopResponseData>, response: Response<GetShopResponseData>) {
                    if (response.isSuccessful) {
                        if(response.body() != null)
                            setupInfo(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<GetShopResponseData>, t: Throwable) {}
            })
        }
    }

    fun setupInfo(info: GetShopResponseData){
        binding.tvShopName.text = info.name
        binding.tvShopAddress.text = info.address
        binding.tvShopShoppingCenter.text = info.shoppingCenter


        info.products?.let {
            mAdapter = DetailCategoryRecyclerViewAdapter(info.products, requireContext())
            binding.rvShopProducts.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }
            mAdapter.onClick = onLikeClick()
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun onLikeClick() : OnClickRecyclerViewListener {
        return object : OnClickRecyclerViewListener {
            override fun onDetailCategoryButton(category: Category) {}
            override fun onProductClick(id: String?) {}

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
                val bundle = Bundle()
                bundle.putString("id",id)
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_shopFragment_to_productFragment, bundle)
            }

            override fun onChatClick() {
                TODO("Not yet implemented")
            }
        }
    }

}