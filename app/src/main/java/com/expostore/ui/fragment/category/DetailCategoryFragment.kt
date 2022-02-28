package com.expostore.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.utils.DetailCategoryRecyclerViewAdapter
import com.expostore.utils.OnClickRecyclerViewListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCategoryFragment :
    BaseFragment<DetailCategoryFragmentBinding>(DetailCategoryFragmentBinding::inflate) {

    private lateinit var detailCategoryViewModel: DetailCategoryViewModel
    private lateinit var mAdapter: DetailCategoryRecyclerViewAdapter
    //lateinit var info: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailCategoryViewModel = ViewModelProvider(this).get(DetailCategoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (context as AppCompatActivity).bottomNavigationView.visibility = View.VISIBLE

        //info = arguments?.getSerializable("category") as Category
        //binding.tvCategoryName.text = info.name

        //mAdapter = DetailCategoryRecyclerViewAdapter(info.products, requireContext())
/*        binding.rvDetailProduct.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = onLikeClick()
        mAdapter.notifyDataSetChanged()*/
    }

    private fun onLikeClick(): OnClickRecyclerViewListener {
        return object : OnClickRecyclerViewListener {
            override fun onDetailCategoryButton(category: Category) {}
            override fun onProductClick(id: String?) {}

            override fun onLikeClick(like: Boolean, id: String?) {

                val token =
                    AppPreferences.getSharedPreferences(requireContext()).getString("token", "")

                if (token.isNullOrEmpty()) {
                    //Todo Подумать над нулевым токеном (возможно кидать на экран авторизации)
                    //navController = Navigation.findNavController(binding.root)
                    //navController.navigate(R.id.action_mainFragment_to_openFragment)
                }

                //val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

                //if (!id.isNullOrEmpty())
                    /*serverApi.selectFavorite("Bearer $token", id)
                        .enqueue(object : Callback<SelectFavoriteResponseData> {
                            override fun onResponse(
                                call: Call<SelectFavoriteResponseData>,
                                response: Response<SelectFavoriteResponseData>
                            ) {
                                try {
                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Toвар добавлен в избранное",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        if (response.errorBody() != null) {
                                            val jObjError =
                                                JSONObject(response.errorBody()!!.string())
                                            val message = jObjError.getString("detail")
                                            if (message.isNotEmpty())
                                                Toast.makeText(context, message, Toast.LENGTH_SHORT)
                                                    .show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.d("exception", e.toString())
                                }
                            }

                            override fun onFailure(
                                call: Call<SelectFavoriteResponseData>,
                                t: Throwable
                            ) {
                                Toast.makeText(
                                    context,
                                    (context as MainActivity).getString(R.string.on_failure_text),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })*/
            }

            override fun onDetailCategoryProductItemClick(id: String?) {
                val bundle = Bundle()
                bundle.putString("id", id)
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(
                    R.id.action_detailCategoryFragment_to_productFragment,
                    bundle
                )
            }

            override fun onChatClick() {
                TODO("Not yet implemented")
            }
        }
    }
}