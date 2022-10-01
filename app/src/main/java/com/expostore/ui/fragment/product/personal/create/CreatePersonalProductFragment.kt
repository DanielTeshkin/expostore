package com.expostore.ui.fragment.product.personal.create

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.CreatePersonalProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.base.fragments.create.CreateProductFragment
import com.expostore.ui.base.vms.CreatorProductViewModel
import com.expostore.ui.fragment.product.addproduct.AddProductViewModel
import com.expostore.ui.fragment.product.addproduct.adapter.ProductCreateImageAdapter
import com.expostore.ui.fragment.product.addproduct.adapter.utils.ImagesEdit
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicState
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
@AndroidEntryPoint
class CreatePersonalProductFragment :
    CreateProductFragment()
    {
        override val viewModel: CreatorProductViewModel by  viewModels()

        override fun onStart() {
            super.onStart()
            btnDraft.visibility=View.GONE
            binding.llPresentation.visibility=View.GONE
            binding.llInstruction.visibility=View.GONE
        }



}