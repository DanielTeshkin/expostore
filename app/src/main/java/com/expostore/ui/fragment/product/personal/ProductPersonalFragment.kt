package com.expostore.ui.fragment.product.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expostore.databinding.ProductPersonalFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductPersonalFragment  :
    BaseFragment<ProductPersonalFragmentBinding>(ProductPersonalFragmentBinding::inflate) {
    private val imageAdapter: ImageAdapter by lazy { ImageAdapter() }
    private val personalViewModel:ProductPersonalViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProductPersonalFragmentArgs.fromBundle(requireArguments()).apply { product?.let { personalViewModel.saveProduct(it)} }
            personalViewModel.apply {
            start(findNavController())
            subscribe(navigation){navigateSafety(it)}
            subscribe(product){init(it)}
                subscribe(comparison){
                    handleState(it){
                          snackbarOpen()
                    }
                }
        }
        binding.comparison.click { personalViewModel.comparison() }

    }

    override fun navigateToComparison() {
        personalViewModel.navigateToComparison()
    }

    fun init(model:ProductModel){
        binding.apply {
            tvProductPrice.text = model.price.priceSeparator()
            tvProductName.text = model.name
            tvProductDescription.text = model.longDescription
            imageAdapter.items = model.images.map { it.file }
            rvProductImages.adapter = imageAdapter
            if (model.elected.notes.isEmpty())binding.editNote.text="Cоздать заметку"
            else  binding.tvProductNote.text=model.elected.notes
            binding.editNote.click { personalViewModel.navigateToNote(model) }
            binding.btnBack.click { personalViewModel.navigateToBack() }
        }
    }

}