package com.expostore.ui.fragment.product.addproduct

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.api.request.ProductRequest
import com.expostore.api.request.createProductRequest
import com.expostore.api.response.ImageResponse
import com.expostore.api.response.ProductResponse
import com.expostore.api.response.ProductResponseUpdate
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.CharacteristicModeL
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.product.addproduct.adapter.ProductCreateImageAdapter
import com.expostore.ui.fragment.product.addproduct.adapter.utils.OnClick
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.other.showCharacteristics
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.imagepicker.*


@AndroidEntryPoint
class AddProductFragment : BaseFragment<AddProductFragmentBinding>(AddProductFragmentBinding::inflate) {
    private  val addProductViewModel: AddProductViewModel by viewModels()
     private lateinit var mAdapter: ProductCreateImageAdapter
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<ProductModel>("product")
            if (result != null) {
                addProductViewModel.saveProductInformation(result)
            }
                init(result)
        }
         val installBottomSheetCategories:Show<List<ProductCategoryModel>> = { installCategories(it)}
         val installBottomSheetCharacteristic : Show<List<CategoryCharacteristicModel>> = { installCharacteristic(it)}
        addProductViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(addProduct){handleState(it)}
            subscribe(categories){handleState(it,installBottomSheetCategories)}
            subscribe(characteristics){ handleState(it,installBottomSheetCharacteristic)}
        }

    }

    override fun onResume() {
        super.onResume()
        clickInstall()
    }

    private fun installCategories(list: List<ProductCategoryModel>){
        binding.llAddProductCategory.click {
            val categoryChose: CategoryChose = {
                addProductViewModel.saveCategory(it.id)
            }
            showBottomSpecifications(list, requireContext(), categoryChose)
        }
    }
    private fun installCharacteristic(list: List<CategoryCharacteristicModel>){
        binding.llAddProductCharacteristic.click {
          showCharacteristics(requireContext(),list,initFilterState())
        }

    }

    fun init(productModel: ProductModel?){
        val onClick=object :OnClick{
            override fun openGallery() {
                addPhoto()
            }

        }
        binding.apply {
            val list= mutableListOf<String>("")
            if (productModel != null) {
                list.addAll(productModel.images.map { it.file })
                addProductTitle.text="Редактировать продукт"
                etProductName.setText(productModel.name)
                etProductCount.setText(productModel.count.toString())
                etProductPrice.setText(productModel.price)
                etProductDescription.setText(productModel.shortDescription)
                etLongDescription.setText(productModel.longDescription)
            }

            rvProductImages.apply {
                layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                mAdapter = ProductCreateImageAdapter(list,onClick)
                adapter=mAdapter
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
    var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result  ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    addProductViewModel.saveUri(fileUri)
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

    private fun clickInstall() {
         binding.apply {
             tvAddProductConnectionsName.selectListener {
                 message.isVisible=it
                 call.isVisible=it
             }
             btnCancel.click {
                   addProductViewModel.navigateToMyProducts()
             }
              tvAddProductCategoryName.click {

              }


             btnSave.click {
              val connect= if(message.isChecked) "chatting"
                 else "call_and_chatting"
                 addProductViewModel.createOrUpdateProduct(
                     requireContext(),
                     etProductCount.stroke().toInt(),
                     etProductName.stroke(),
                     etProductPrice.stroke(),
                     etLongDescription.stroke(),
                     etProductDescription.stroke(), "my",connect) }

             binding.btnSaveDraft.click {
                 val connect= if(message.isChecked) "chatting"
                 else if(call.isChecked) "call_and_chatting"
                 else{"calls"}
                 addProductViewModel.createOrUpdateProduct(
                     requireContext(),
                     etProductCount.stroke().toInt(),
                     etProductName.stroke(),
                     etProductPrice.stroke(),
                     etLongDescription.stroke(),
                     etProductDescription.stroke(), "draft",connect)
             }
         }
     }
    private fun initFilterState() : FilterState {
        return  object : FilterState {
            override fun inputListener(left: String, right: String?, name: String) {
                Log.i("input",name)
                addProductViewModel.addFilterInput(left,right?:"",name)
            }

            override fun radioListener(id: String, name: String) {
                Log.i("radio",name)
                addProductViewModel.addFilterRadio(id,name)
            }

            override fun selectListener(id:String,name: String,checked: Boolean) {
                Log.i("select",name)
                when(checked){
                  //  true-> myAdapter.addSelect(id)
                   // false->myAdapter.removeSelect(id)
                }
                //addProductViewModel.addFilterSelect(name,myAdapter.selectList)
            }

            override fun checkBoxListener(name: String, checked: Boolean) {
                Log.i("check",checked.toString())
                addProductViewModel.addFilterCheckbox(name,checked)
            }

        }
    }


}

  fun EditText.stroke():String = text.toString()
