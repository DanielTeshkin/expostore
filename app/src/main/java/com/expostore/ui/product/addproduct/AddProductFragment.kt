package com.expostore.ui.product.addproduct

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.R
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.ui.base.BaseFragment

class AddProductFragment : BaseFragment<AddProductFragmentBinding>(AddProductFragmentBinding::inflate) {

    private lateinit var addProductViewModel: AddProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addProductViewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addProductViewModel.context = requireContext()
        binding.llAddProductCategory.setOnClickListener {
            addProductViewModel.getProductCategory(it)
        }
    }

}