package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel

import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.product.addproduct.adapter.ProductCreateImageAdapter
import com.expostore.ui.fragment.product.addproduct.adapter.utils.ImagesEdit
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicsStateModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductController(context: Context,
                           private val binding:AddProductFragmentBinding,
                           characteristicsStateModel: CharacteristicsStateModel,
                           private val saveAction:(List<Bitmap>)->Unit,
                           private val saveFile:(List<SaveFileRequestData>)->Unit,
                           private val loadCharacteristics:(String)->Unit,
                           private val  addPhoto:()->Unit
)
    : BaseCreatorController(context),ImagesEdit{
    private  val mAdapter: ProductCreateImageAdapter by lazy {
        ProductCreateImageAdapter(list, this)
    }

    private val characteristicAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(context,this,"other",characteristicsStateModel)
    }
     private val instruction = MutableStateFlow<Uri>(Uri.parse(""))
    private val presentation = MutableStateFlow<Uri>(Uri.parse(""))
    private val fileRequestData= mutableListOf<SaveFileRequestData>()
    val processor= ConditionProcessor()
    val imagesForRequest= mutableListOf<Bitmap>()




    private fun installObserverConnectionType(){
        binding.apply {
            message.setOnCheckedChangeListener { _, state->  }
            call.setOnCheckedChangeListener { _, state ->  }
        }
    }

    override fun checkEnabled() {
        super.checkEnabled()
        updateEnabledState(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
                and shortDescription.value.isNotEmpty() and count.value.isNotEmpty() and price.value.isNotEmpty() and(
                (imageList.value.size!=0) or (uriSource.value.size!=0)))
        btnEnabledObserver(enabled.value)
    }

    override fun saveMultimedia() {
        processor.checkConditionParameter(
            condition= arrayOf(
                {presentation.value!= Uri.parse("")},
                {instruction.value!= Uri.parse("")}
            ),
                actionTrue = {states->loadPhoto(states)},
                actionFalse = {checkPhotos()}
            )
    }

    private fun checkPhotos(){
        processor.checkCondition(
            { multimedia.isNotEmpty() },
            actionTrue = {saveAction.invoke(mapImages.entries.map { it.value })}
        )
    }

   private fun Uri.castFileToRequestData():SaveFileRequestData{
      return FileStorage(context).getSaveRequestData(this)
   }

    private fun loadPhoto(list: List<Boolean>) {
        val processor= ConditionProcessor()
        processor.apply {
            checkCondition(condition={list[0]},
                actionTrue = { fileRequestData.add(presentation.value.castFileToRequestData())})
            checkCondition(
                { list[1] },
                actionTrue = { fileRequestData.add(instruction.value.castFileToRequestData())}
            )
        }
        saveFile.invoke(fileRequestData)
    }

    override fun update() {

    }

    init {
        binding.apply {
            message.setOnCheckedChangeListener { _, state-> changeConnectionType(state,"message") }
            call.setOnCheckedChangeListener { _, state -> changeConnectionType(state,"call") }
            etProductName.addTextChangedListener {changeName(it.toString()) }
            etProductPrice.addTextChangedListener {changePrice(it.toString()) }
            etProductDescription.addTextChangedListener {changeShortDescription(it.toString())}
            etProductCount.addTextChangedListener {changeCount(it.toString())}
            etLongDescription.addTextChangedListener {changeLongDescription(it.toString())}
            rvProductImages.apply {
                layoutManager=
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                adapter=mAdapter
            }
            tvAddProductConnectionsName.selectListener {
                message.isVisible=it
                call.isVisible=it
            }
           btnSave.click {
               updateStatus("my")
               createOrUpdate()
           }

            btnSaveDraft.click {
                updateStatus("draft")
                createOrUpdate() }
        }
    }


    fun saveProduct( status:String,productUpdateRequest: ProductUpdateRequest,action:(ProductUpdateRequest,String)->Unit){
          action.invoke(productUpdateRequest,status)
    }

    fun characteristicLoad(list: List<CategoryCharacteristicModel>){
        binding.apply {
            llAddProductCharacteristic.apply {
                visibility = View.VISIBLE
                characteristicAdapter.removeAll()
                characteristicAdapter.addElement(list)
                rvProductCharacteristic.layoutManager=LinearLayoutManager(context)
                rvProductCharacteristic.adapter=characteristicAdapter
                selectListener {
                    rvProductCharacteristic.isVisible=it
                }
            }
        }
    }

    private fun installCategories(list: List<ProductCategoryModel>){
        binding.llAddProductCategory.click {
            val categoryChose: CategoryChose = {
                category.value=it.id
                loadCharacteristics.invoke(it.id) }
            showBottomSpecifications(list, context, categoryChose)
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
    fun updateAdapter(uri: Uri)=mAdapter.update(uri.toString())

    private fun btnEnabledObserver(state:Boolean){
        binding.apply {
                btnSave.isEnabled = state
                btnSaveDraft.isEnabled = state
            } }

    override fun openGallery() {
      addPhoto
    }

    override fun removePhoto(string: String) =removeImage(string)

    override fun checkUriSource() : Boolean{
       return (uriSource.value.isNullOrEmpty()&&
               instruction.value!= Uri.parse("")
               && presentation.value!= Uri.parse(""))
    }


}