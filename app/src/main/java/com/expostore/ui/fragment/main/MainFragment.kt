package com.expostore.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.expostore.data.AppPreferences
import com.expostore.databinding.MainFragmentBinding
import com.expostore.extension.load
import com.expostore.model.category.CategoryAdvertisingModel

import com.expostore.model.category.SelectionModel
import com.expostore.model.profile.ProfileModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.fragment.main.adapter.CategoriesAdapter
import com.expostore.ui.fragment.profile.ShopInfoModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.state.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()
    private var adapter = CategoriesAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToken()
        viewModel.load()
        //viewModel.getAdvertisingCategory()
        //viewModel.getSelections()
       // viewModel.getProfile()
        //viewModel.getAdvertisingCategory()
       subscribeOnChangeData()


         }

    override fun onStart() {
        super.onStart()

        binding.btnAddAdvertisement.click {
            viewModel.navigateToCreateProductOrOpen()
        }
        binding.categories.adapter = adapter

        adapter.onCategoryClickListener = {
            val result=it
            setFragmentResult("requestKey", bundleOf("selection" to result))
            viewModel.navigateToSelectionFragment()
        }

        adapter.onProductClickListener = {
            val result=it
            setFragmentResult("requestKey", bundleOf("product" to result))
            viewModel.navigateToProduct()
        }

        binding.ivProfile.click { viewModel.navigateToProfileOrOpen() }
       // viewModel.getAdvertisingCategory()
    }


    private fun subscribeOnChangeData(){
        val loadSelections:Show<List<SelectionModel>> = {handleCategories(it)}
        val loadProfile : Show<ProfileModel> = { handleProfile(it)}
        val loadAdvertisingModel : Show<List<CategoryAdvertisingModel>> = { handleAdvertising(it)}
        viewModel.apply {
            subscribe(uiState) { handleState(it) }
            subscribe(navigation){navigateSafety(it)}
            //  subscribe(selection){handleState(it,loadSelections)}
          //  subscribe(profile){handleState(it,loadProfile)}
            subscribe(advertisingModel) { handleState(it, loadAdvertisingModel) }
        }
    }



    private fun handleState(state: MainState) {
        when (state) {
            is MainState.Loading -> handleLoading(state.isLoading)
            is MainState.Error -> handleError(state.throwable)
            is MainState.SuccessCategory -> handleCategories(state.items)
            is MainState.SuccessAdvertising -> handleAdvertising(state.items)
            is MainState.SuccessProfile -> handleProfile(state.item)
        }
    }

    private fun handleProfile(item: ProfileModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            item.avatar?.let { binding.ivProfile.loadAvatar(it.file) }
            viewModel.saveProfileInfo(item)
        }
    }


    private fun handleCategories(items: List<SelectionModel>) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            adapter.submitList(items)
        }

    }

    private fun handleAdvertising(items: List<CategoryAdvertisingModel>) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.ivAdvertising.load(items[0].image)
        }
    }

    private fun handleError(throwable: Throwable) {
        // TODO: сделать отображение ошибки с сервера
        //временная реализация
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        // TODO: сделать отображение загрузки
    }

}
