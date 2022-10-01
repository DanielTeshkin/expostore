package com.expostore.ui.fragment.tender.create

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.data.remote.api.pojo.gettenderlist.TenderRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderResponse
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData

import com.expostore.databinding.TenderCreateFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.base.fragments.create.BaseCreatorFragment
import com.expostore.ui.base.vms.BaseCreatorViewModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicState
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TenderCreateFragment :
    BaseCreatorFragment<TenderCreateFragmentBinding,TenderResponse,
            TenderResponse,TenderRequest,TenderModel>(TenderCreateFragmentBinding::inflate) {

    override val item: TenderModel?=TenderCreateFragmentArgs.fromBundle(requireArguments()).tender
    override val rvImages: RecyclerView = binding.rvTenderImages
    override val filter: String = "other"
    override val categoriesLayout: LinearLayout = binding.llTenderCreateCategory
    override val characteristicsLayout: LinearLayout = binding.llAddTenderCharacteristic
    override val rvCharacteristics: RecyclerView = binding.rvTenderCharacteristic
    override val call: CheckBox = binding.call
    override val message: CheckBox = binding.message
    override val viewModel: TenderCreateViewModel by viewModels()
    override val connectionLayout = binding.llAddProductConnections
    override val btnSave= binding.btnSave
    override val btnDraft=binding.btnSaveDraft
    override val btnCancel=binding.btnCancel


       override fun loadSaveInformation(item: TenderModel) {
        binding.apply {
            item.apply {
                images?.let { images -> list.addAll(images.map { it.file }) }
                title.let {
                    etTenderName.setText(it)
                    viewModel.changeName(it ?: "")
                }
                description.let {
                    etTenderDescription.setText(it)
                    viewModel.changeDescription(it.toString())
                }
                location.let {
                    etTenderLocation.setText(it)
                    viewModel.changeLocation(it.toString())
                }
                count.let {
                    etTenderCount.setText(it)
                    viewModel.changeCount(it.toString())
                }
                price.let {
                    etTenderPrice.setText(it)
                    viewModel.changePrice(it.toString())
                }
                if (characteristicModel != null) {
                    viewModel.saveCharacteristic(characteristicModel)
                }


            }
        }
    }

    override fun setTextChangeListeners() {
      binding.apply {
          etTenderDescription.addTextChangedListener {viewModel.changeDescription(it.toString()) }
          etTenderCount.addTextChangedListener { viewModel.changeCount(it.toString()) }
          etTenderPrice.addTextChangedListener { viewModel.changePrice(it.toString()) }
          etTenderName.addTextChangedListener { viewModel.changeName(it.toString()) }
          etTenderLocation.addTextChangedListener { viewModel.changeLocation(it.toString()) }
      }
    }
}