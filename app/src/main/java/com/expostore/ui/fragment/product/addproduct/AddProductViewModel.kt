package com.expostore.ui.fragment.product.addproduct


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveFileResponseData
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.base.interactors.CreateProductInteractor
import com.expostore.ui.base.vms.CreatorProductViewModel
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel
@Inject constructor(override val interactor: CreateProductInteractor
) : CreatorProductViewModel() {
    private val instruction = MutableStateFlow<Uri>(Uri.parse(""))
    private val presentation = MutableStateFlow<Uri>(Uri.parse(""))
    private val fileRequestData= mutableListOf<SaveFileRequestData>()
    val flag= MutableStateFlow("")
    @SuppressLint("StaticFieldLeak")
    private var context:Context? = null
    private val _files=MutableSharedFlow<ResponseState<SaveFileResponseData>>()
    val files=_files.asSharedFlow()
    val fileList= mutableListOf<String?>(null,null)
    init { getCategories() }

    fun updateFlag(mean:String){
        flag.value=mean
    }
    fun saveContext(actual: Context){
        context=actual
    }
    fun saveShopId(id:String){
        interactor.saveShopId(id)
    }
    fun saveInstruction(uri: Uri){
        instruction.value=uri
    }
    fun savePresentation(uri: Uri){
        presentation.value=uri
    }

    fun getProduct(id: String)=interactor.getProduct(id).handleResult({updateEnabledState(it)},{
        navigateToMyItem(it)
    })

    private fun navigateToMyItem(model: ProductModel) = navigationTo(AddProductFragmentDirections.actionAddProductFragmentToEditMyProduct(model))
    override fun checkEnabled() {
        updateEnabledState(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
                and shortDescription.value.isNotEmpty() and count.value.isNotEmpty() and price.value.isNotEmpty() and(
                (imageList.value.size!=0) or (getBitmapList().isNotEmpty())))
    }

    override fun checkStackMultimedia(): Boolean {
       return (getBitmapList().isNotEmpty() ||
               instruction.value!= Uri.parse("")
               || presentation.value!= Uri.parse(""))
    }

    override fun saveMultimedia() {
        processor.checkConditionParameter(
            condition= arrayOf({presentation.value!= Uri.parse("")},
                {instruction.value!= Uri.parse("")}
            ),
            actionTrue = {states->loadFiles(states)},
            actionFalse = {checkPhoto()}
        )
    }
    fun addFiles(list: List<String>){
        fileList.addAll(list)
        checkPhoto()
    }
    private fun loadFiles(list: List<Boolean>) {
        val processor= ConditionProcessor()
        processor.apply {
            checkCondition(condition={list[0]},
                actionTrue = { fileRequestData.add(presentation.value.castFileToRequestData())})
            checkCondition(
                { list[1] },
                actionTrue = { fileRequestData.add(instruction.value.castFileToRequestData())}
            )
        }

        interactor.saveFiles(fileRequestData).handleResult(_files)
    }

    private fun Uri.castFileToRequestData():SaveFileRequestData{
        return context?.let { FileStorage(it).getSaveRequestData(this) }?: SaveFileRequestData()
    }
}