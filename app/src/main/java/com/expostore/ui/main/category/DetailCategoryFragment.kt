package com.expostore.ui.main.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.databinding.MainFragmentBinding
import com.expostore.ui.main.MainViewModel
import com.expostore.utils.CategoryRecyclerViewAdapter
import com.expostore.utils.DetailCategoryRecyclerViewAdapter
import com.expostore.utils.OnClickListener
import com.expostore.utils.OnClickRecyclerViewListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class DetailCategoryFragment : Fragment() {

    private lateinit var binding: DetailCategoryFragmentBinding
    private lateinit var detailCategoryViewModel: DetailCategoryViewModel
    private lateinit var mAdapter: DetailCategoryRecyclerViewAdapter
    lateinit var info: Category

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_category_fragment, container, false)
        detailCategoryViewModel = ViewModelProvider(this).get(DetailCategoryViewModel::class.java)
        binding.detailCategoryVM = detailCategoryViewModel

        (context as AppCompatActivity).bottomNavigationView.visibility = View.VISIBLE

        info = arguments?.getSerializable("category") as Category
        binding.tvCategoryName.text = info.name

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = DetailCategoryRecyclerViewAdapter(info.products, requireContext())
        binding.rvDetailProduct.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = onLikeClick()
        mAdapter.notifyDataSetChanged()
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
                        catch (e:Exception){
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
                navController.navigate(R.id.action_detailCategoryFragment_to_productFragment, bundle)
            }
        }
    }
}