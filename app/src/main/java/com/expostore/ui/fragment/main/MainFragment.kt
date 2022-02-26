package com.expostore.ui.fragment.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.api.pojo.getcategory.Category
import com.expostore.data.AppPreferences
import com.expostore.databinding.MainFragmentBinding
import com.expostore.model.category.CategoryModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.main.adapter.CategoriesAdapter
import com.expostore.ui.state.ResponseState
import com.expostore.utils.CategoryRecyclerViewAdapter
import com.expostore.utils.OnClickRecyclerViewListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    //private lateinit var mainViewModel: MainViewModel
    private lateinit var navController: NavController
    private var adapter = CategoriesAdapter()

    private val mainViewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddAdvertisement.setOnClickListener {
            mainViewModel.navigateToAddProduct(it)
        }
        binding.ivProfile.setOnClickListener {
            mainViewModel.navigateToProfile(it)
        }

        binding.categories.adapter = adapter

        mainViewModel.apply {
            subscibe(uiState) { handleState(it) }
            start()
        }
    }

    private fun handleState(state: ResponseState<List<CategoryModel>>) {
        when (state) {
            is ResponseState.Loading -> handleLoading(state.isLoading)
            is ResponseState.Error -> handleError(state.throwable)
            is ResponseState.Success -> handleItems(state.item)
        }
    }
    private fun handleItems(items: List<CategoryModel>) {
        adapter.submitList(items)
    }

    private fun handleError(throwable: Throwable) {
        // TODO: сделать отображение ошибки с сервера
        //временная реализация
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleLoading(loading: Boolean) {
        // TODO: сделать отображение загрузки
    }

    //TODO авторизация не работает
    override fun onStart() {
        super.onStart()

        //(context as MainActivity).binding.bottomNavigationView.visibility = View.VISIBLE


        val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")

        if (token.isNullOrEmpty()) {
            navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.action_mainFragment_to_openFragment)
        }

        /*val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.getCategories("Bearer $token").enqueue(object : Callback<ArrayList<Category>> {
            override fun onResponse(call: Call<ArrayList<Category>>, response: Response<ArrayList<Category>>) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        mAdapter = CategoryRecyclerViewAdapter(response.body(), requireContext())
                        binding.rvCategories.apply {
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = mAdapter
                        }
                        mAdapter.onClick = onClickRecyclerItems()
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Category>>, t: Throwable) {}
        })

        serverApi.getCategoryAdvertising("Bearer $token").enqueue(object : Callback<ArrayList<CategoryAdvertising>> {
            override fun onResponse(call: Call<ArrayList<CategoryAdvertising>>, response: Response<ArrayList<CategoryAdvertising>>) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        val firstItem = response.body()!![0]
                        Picasso.get().load(firstItem.image).into(binding.ivAdvertising)

                        binding.ivAdvertising.setOnClickListener{
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data = Uri.parse(response.body()!![0].url)
                            startActivity(openURL)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<CategoryAdvertising>>, t: Throwable) {
            }
        })*/
    }

    private fun onClickRecyclerItems(): OnClickRecyclerViewListener {
        return object : OnClickRecyclerViewListener {
            override fun onLikeClick(like: Boolean, id: String?) {}
            override fun onDetailCategoryProductItemClick(id: String?) {}
            override fun onChatClick() {
                TODO("Not yet implemented")
            }

            override fun onDetailCategoryButton(category: Category) {
                val bundle = Bundle()
                bundle.putSerializable("category", category)
                navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_mainFragment_to_detailCategoryFragment, bundle)

            }

            override fun onProductClick(id: String?) {
                val bundle = Bundle()
                bundle.putString("id", id)
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_mainFragment_to_productFragment, bundle)
            }
        }
    }
}