package com.expostore.ui.product.addproduct

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.databinding.AddReviewFragmentBinding
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.ui.product.ProductViewModel
import com.google.android.gms.maps.GoogleMap

class AddProductFragment : Fragment() {

    private lateinit var binding: AddProductFragmentBinding
    private lateinit var addProductViewModel: AddProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_product_fragment, container, false)
        addProductViewModel = ViewModelProvider(this).get(AddProductViewModel::class.java)
        addProductViewModel.context = requireContext()
        binding.addProductVM = addProductViewModel

        return binding.root
    }

}