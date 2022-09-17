package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.request.createProductRequest
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel

import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicsStateModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductController(context: Context,
                           private val binding:AddProductFragmentBinding,
                           private val saveAction:(List<Bitmap>)->Unit,
                           private val saveFile:(List<SaveFileRequestData>)->Unit,
                           private val loadCharacteristics:(String)->Unit,
                           private val  addPhoto:()->Unit,
                           private val action: (ProductUpdateRequest, String) -> Unit,
                           override val fieldCategory: LinearLayout =binding.llAddProductCategory,
                           override var fieldCharacteristic: LinearLayout=binding.llAddProductCharacteristic,
                           override val recycleViewCharacteristics: RecyclerView=binding.rvProductCharacteristic

) : BaseCreatorController(context,addPhoto,  loadCharacteristics){
    private val instruction = MutableStateFlow<Uri>(Uri.parse(""))
    private val presentation = MutableStateFlow<Uri>(Uri.parse(""))
    private val fileRequestData= mutableListOf<SaveFileRequestData>()
    val files= mutableListOf<String?>(null,null)

    override fun checkEnabled() {
        super.checkEnabled()
        updateEnabledState(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
                and shortDescription.value.isNotEmpty() and count.value.isNotEmpty() and price.value.isNotEmpty() and(
                (imageList.value.size!=0) or (getBitmapList().isNotEmpty())))
        btnEnabledObserver(enabled.value)
    }
    override fun saveMultimedia() {
        Log.i("log","ddd")
        processor.checkConditionParameter(
            condition= arrayOf(
                {presentation.value!= Uri.parse("")},
                {instruction.value!= Uri.parse("")}
            ),
                actionTrue = {states->loadPhoto(states)},
                actionFalse = {checkPhotos()}
            )
    }
    fun initAdapter(){
        binding.apply {
            message.setOnCheckedChangeListener { _, state-> changeConnectionType(state,"message") }
            call.setOnCheckedChangeListener { _, state -> changeConnectionType(state,"call") }
            etProductName.addTextChangedListener {changeName(it.toString()) }
            etProductPrice.addTextChangedListener {changePrice(it.toString()) }
            etProductDescription.addTextChangedListener {changeShortDescription(it.toString())}
            etProductCount.addTextChangedListener {changeCount(it.toString())}
            etLongDescription.addTextChangedListener {changeLongDescription(it.toString())}


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
        binding.rvProductImages.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter=mAdapter
        }
    }

   fun checkPhotos(){
        processor.checkCondition(
            { multimedia.isNotEmpty() },
            actionTrue = {saveAction.invoke(mapImages.entries.map { it.value })},
            actionFalse = {update()}
        )
   }

    fun addFiles(list: List<String>) {
        files.addAll(list)
        checkPhotos()
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
        Log.i("crash","ddd")
         action.invoke(createProductRequest(
             count.value.toInt(),
             name = name.value,
             price = price.value,
             longDescription = longDescription.value,
             shortDescription.value,
             imageList.value,
             communicationType = connectionType.value,
             characteristicLoad(),
             category.value,
             ),status)
    }



    fun init(productModel: ProductModel){
        binding.apply {
            val images=productModel.images
           imageList.value.addAll(images.map { it.id })
            list.addAll(images.map { it.file })
            addProductTitle.text="Редактировать продукт"
            etProductName.setText(productModel.name)
            changeName(productModel.name)
            etProductCount.setText(productModel.count.toString())
            etProductPrice.setText(productModel.price)
            etProductDescription.setText(productModel.shortDescription)
            etLongDescription.setText(productModel.longDescription)
            category.value=productModel.category?.id?:""
            saveCharacteristic(productModel.characteristics) } }
    private fun btnEnabledObserver(state:Boolean){
        binding.apply {
                btnSave.isEnabled = state
                btnSaveDraft.isEnabled = state
            } }
    override fun checkUriSource() : Boolean= (multimedia.size> 0 ||
               instruction.value!= Uri.parse("")
               || presentation.value!= Uri.parse(""))
}