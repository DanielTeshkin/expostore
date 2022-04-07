package com.expostore.ui.fragment.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.expostore.databinding.MainFragmentBinding
import com.expostore.extension.load
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.CategoryModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.main.adapter.CategoriesAdapter
import com.expostore.ui.state.MainState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    private var adapter = CategoriesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddAdvertisement.setOnClickListener {
            navigateSafety(MainFragmentDirections.actionMainFragmentToAddProductFragment())
        }
        binding.ivProfile.setOnClickListener {
            navigateSafety(MainFragmentDirections.actionMainFragmentToProfileFragment())
        }

        binding.categories.adapter = adapter

        adapter.onCategoryClickListener = {
            navigateSafety(MainFragmentDirections.actionMainFragmentToDetailCategoryFragment(it))
        }

        adapter.onProductClickListener = {
            navigateSafety(MainFragmentDirections.actionMainFragmentToProductFragment(it))
        }

        viewModel.apply {
            subscribe(uiState) { handleState(it) }
            start()
        }
    }

    private fun handleState(state: MainState) {
        when (state) {
            is MainState.Loading -> handleLoading(state.isLoading)
            is MainState.Error -> handleError(state.throwable)
            is MainState.SuccessCategory -> handleCategories(state.items)
            is MainState.SuccessAdvertising -> handleAdvertising(state.items)
        }
    }

    private fun handleCategories(items: List<CategoryModel>) {
        adapter.submitList(items)
    }

    private fun handleAdvertising(items: List<CategoryAdvertisingModel>) {
        items.firstOrNull()?.let {
            binding.ivAdvertising.load(it.url)
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