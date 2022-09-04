package com.expostore.ui.fragment.product.personal

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.ProductPersonalFragmentBinding
import com.expostore.extension.lastElement
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.ui.fragment.product.ProductViewModel
import com.expostore.ui.fragment.product.ReviewsModel
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class ProductPersonalFragment  :
    BaseFragment<ProductPersonalFragmentBinding>(ProductPersonalFragmentBinding::inflate) {
    private val imageAdapter: ImageAdapter by lazy { ImageAdapter() }
    private val personalViewModel:ProductPersonalViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personal=ProductPersonalFragmentArgs.fromBundle(requireArguments()).product
        init(personal)
        personalViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }
    }

    fun init(model:ProductModel){
        binding.apply {
            tvProductPrice.text = model.price.priceSeparator()
            tvProductName.text = model.name
            tvProductDescription.text = model.longDescription
            imageAdapter.items = model.images.map { it.file }
            rvProductImages.adapter = imageAdapter
            tvProductLocation.text=model.shop.address
            if(model.count>0){
                tvProductAvailable.setTextColor(Color.BLUE)
                tvProductAvailable.text="В наличии"
            }

            if (model.elected==null)binding.editNote.text="Cоздать заметку"
            else  binding.tvProductNote.text=model.elected.notes
            binding.editNote.click { personalViewModel.navigateToNote(model) }
            binding.btnBack.click { personalViewModel.navigateToBack() }
        }
    }

}