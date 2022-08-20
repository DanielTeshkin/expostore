package com.expostore.ui.fragment.product.addproduct

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
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
class AddProductFragment : BaseFragment<AddProductFragmentBinding>(AddProductFragmentBinding::inflate),
   CharacteristicState,ImagesEdit {
    private  val addProductViewModel: AddProductViewModel by viewModels()
    private val list= mutableListOf("")
    private val selectList = mutableListOf<String>()

    private  val mAdapter: ProductCreateImageAdapter by lazy {
        ProductCreateImageAdapter(list, this)
    }

    private val characteristicAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(requireContext(),this,"other",addProductViewModel.characteristicState.value)
    }
    private val installBottomSheetCategories:Show<List<ProductCategoryModel>> = { installCategories(it)}
    private val installBottomSheetCharacteristic : Show<List<CategoryCharacteristicModel>> = { installCharacteristic(it)}
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
     }


    override fun onStart() {
        super.onStart()
        clickInstall()
        installObserversButtonEnabled()
        installObserverConnectionType()
    }

    private fun setup(){
        makeStartRequest()
        installNetworkObserverStateUi()
        installSetResultListeners()
        installButtonsState()
    }

    private fun makeStartRequest()=addProductViewModel.loadCategories()

    private fun installNetworkObserverStateUi(){
        addProductViewModel.apply {
            subscribe(navigation) { navigateSafety(it) }
            subscribe(addProduct) { handleState(it) }
            subscribe(categories) { handleState(it, installBottomSheetCategories) }
            subscribe(characteristics) { handleState(it, installBottomSheetCharacteristic) }
            subscribe(productPublic) { handleState(it) }
        }
    }
    private fun installButtonsState(){
        state {
            addProductViewModel.enabled.collect {
                binding.apply {
                    btnSave.isEnabled = it
                    btnSaveDraft.isEnabled=it
                }
            }
        }
        state {
            addProductViewModel.connectionTypeState.collect { state->
                binding.apply {
                    call.isChecked=!state
                    message.isChecked=state
                }
            }
        }
    }

    private fun installObserverConnectionType(){
        binding.apply {
            message.setOnCheckedChangeListener { _, state-> addProductViewModel.updateConnectionTypeState(state) }
            call.setOnCheckedChangeListener { _, state -> addProductViewModel.updateConnectionTypeState(!state) }
        }
    }

    private fun installObserversButtonEnabled(){
        binding.apply {
            etProductName.addTextChangedListener {addProductViewModel.changeName(it.toString()) }
            etProductPrice.addTextChangedListener {addProductViewModel.changePrice(it.toString()) }
            etProductDescription.addTextChangedListener {addProductViewModel.changeShortDescription(it.toString())}
            etProductCount.addTextChangedListener {addProductViewModel.changeCount(it.toString())}
            etLongDescription.addTextChangedListener {addProductViewModel.changeLongDescription(it.toString())}
        }

    }

    private fun installSetResultListeners(){
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<ProductModel>("product")
            if (result != null) {
                addProductViewModel.saveProductInformation(result)
                init(result)
            }
        }
        setFragmentResultListener("shop") { _, bundle ->
            val result = bundle.getString("id")
            if (result != null) {
                addProductViewModel.saveShopId(result)
            }
        }
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
        binding.apply {
            llAddProductCharacteristic.apply {
                visibility = View.VISIBLE
                characteristicAdapter.addElement(list)
                rvProductCharacteristic.layoutManager=LinearLayoutManager(requireContext())
                rvProductCharacteristic.adapter=characteristicAdapter
                selectListener {
                 rvProductCharacteristic.isVisible=it
                }
            }
        }
    }

    fun init(productModel: ProductModel){
        binding.apply {
            list.addAll(productModel.images.map { it.file })
                addProductTitle.text="Редактировать продукт"
                etProductName.setText(productModel.name)
                etProductCount.setText(productModel.count.toString())
                etProductPrice.setText(productModel.price)
                etProductDescription.setText(productModel.shortDescription)
                etLongDescription.setText(productModel.longDescription)
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

             rvProductImages.apply {
                 layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                 adapter=mAdapter
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

    override fun inputListener(left: String, right: String?, name: String) {
        addProductViewModel.addFilterInput(left,right?:"",name)
    }

    override fun radioListener(id: String, name: String) {
        addProductViewModel.addFilterRadio(id,name)
    }

    override fun selectListener(id: String, name: String, checked: Boolean) {
        when(checked){
            true-> selectList.add(id)
            false->selectList.remove(id)
        }
        addProductViewModel.addFilterSelect(name,selectList)
    }

    override fun checkBoxListener(name: String, checked: Boolean) {
        addProductViewModel.addFilterCheckbox(name,checked)
    }

    override fun openGallery() {
       addPhoto()
    }

    override fun removePhoto(string: String) {
       addProductViewModel.removeImage(string)
    }
}
    fun EditText.stroke():String = text.toString()
