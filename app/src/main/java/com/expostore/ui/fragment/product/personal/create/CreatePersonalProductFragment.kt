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
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
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
    BaseFragment<CreatePersonalProductFragmentBinding>(CreatePersonalProductFragmentBinding::inflate),
    CharacteristicState, ImagesEdit {

        private val viewModel:AddProductViewModel by viewModels()
    private val list= mutableListOf("")
    private val selectList = mutableListOf<String>()

    private  val mAdapter: ProductCreateImageAdapter by lazy {
        ProductCreateImageAdapter(list, this)
    }

    private val characteristicAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(requireContext(),this,"other")
    }
    private val installBottomSheetCategories:Show<List<ProductCategoryModel>> = { installCategories(it)}
    private val installBottomSheetCharacteristic : Show<List<CategoryCharacteristicModel>> = { installCharacteristic(it)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         viewModel.apply {
             subscribe(navigation) { navigateSafety(it) }
             subscribe(addProduct) { handleState(it) }
             subscribe(categories) { handleState(it, installBottomSheetCategories) }
             subscribe(characteristics) { handleState(it, installBottomSheetCharacteristic) }
         }
    }

    override fun onStart() {
        super.onStart()
        binding.apply {
            etProductName.addTextChangedListener {  viewModel.changeName(it.toString()) }
            etProductPrice.addTextChangedListener {  viewModel.changePrice(it.toString()) }
            etLongDescription.addTextChangedListener { viewModel.changeShortDescription(it.toString()) }
            etProductCount.addTextChangedListener {  viewModel.changeCount(it.toString()) }
            etLongDescription.addTextChangedListener { viewModel.changeLongDescription(it.toString()) }
            state { viewModel.enabled.collect { btnSave.isEnabled=it } }
            btnSave.click { viewModel.createPersonalProduct(requireContext()) }
            rvProductImages.apply {
                layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapter=mAdapter
            }
        }
    }




    private fun installCategories(list: List<ProductCategoryModel>){
        binding.llAddProductCategory.click {
            val categoryChose: CategoryChose = {
                viewModel.saveCategory(it.id)
            }
            showBottomSpecifications(list, requireContext(), categoryChose)
        }
    }
    private fun installCharacteristic(list: List<CategoryCharacteristicModel>){
        binding.apply {
            llAddProductCharacteristic.apply {
                visibility = View.VISIBLE
                characteristicAdapter.removeAll()
                characteristicAdapter.addElement(list)
                rvProductCharacteristic.layoutManager= LinearLayoutManager(requireContext())
                rvProductCharacteristic.adapter=characteristicAdapter
                selectListener {
                    rvProductCharacteristic.isVisible=it
                }
            }
        }
    }






    private fun addPhoto() {
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent -> launchSomeActivity.launch(intent) }
    }
    private var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result  ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                   viewModel.saveUri(fileUri)
                    mAdapter.update(fileUri.toString())
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Вы вышли", Toast.LENGTH_SHORT).show()
                }
            }
        }
    override fun inputListener(left: String, right: String?, name: String) {
       viewModel.addFilterInput(left,right?:"",name)
    }

    override fun radioListener(id: String, name: String) {
        viewModel.addFilterRadio(id,name)
    }

    override fun selectListener(id: String, name: String, checked: Boolean) {
        when(checked){
            true-> selectList.add(id)
            false->selectList.remove(id)
        }
        viewModel.addFilterSelect(name,selectList)
    }

    override fun checkBoxListener(name: String, checked: Boolean) {
        viewModel.addFilterCheckbox(name,checked)
    }

    override fun openGallery() {
        addPhoto()
    }

    override fun removePhoto(string: String) {
       viewModel.removeImage(string)
    }


}