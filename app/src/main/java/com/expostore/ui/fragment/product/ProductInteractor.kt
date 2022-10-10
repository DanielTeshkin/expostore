package com.expostore.ui.fragment.product

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.request.createProductRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.PersonalProductRequest
import com.expostore.data.repositories.*
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.model.category.toRequestCreate
import com.expostore.model.product.Character
import com.expostore.ui.base.interactors.BaseProductInteractor
import com.expostore.ui.fragment.chats.general.ImageMessage
import com.expostore.ui.general.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ProductInteractor @Inject constructor(private val productsRepository: ProductsRepository,
                                            private val categoryRepository: CategoryRepository,
                                            private val profileRepository: ProfileRepository,
                                            private val multimediaRepository: MultimediaRepository,
                                            private val chatRepository: ChatRepository)

{
    private val _imageList=MutableStateFlow<MutableList<String>>(mutableListOf())
     private val imageList=_imageList.asStateFlow()
    val uriSource=MutableStateFlow<MutableList<Uri>>(mutableListOf())
    private val _enabled=MutableStateFlow(false)
    val enabled=_enabled.asStateFlow()
    private val _characteristicState= MutableStateFlow(CharacteristicsStateModel())
    val  characteristicState=_characteristicState.asStateFlow()
    private val name= MutableStateFlow("")
    private val longDescription = MutableStateFlow("")
    private val shortDescription= MutableStateFlow("")
    private val count = MutableStateFlow("")
    private val price = MutableStateFlow("")
    private val category = MutableStateFlow("")
    private val connectionType = MutableStateFlow("call_and_chatting")
     val shop= MutableStateFlow("")


    fun getCharacteristicsState()=characteristicState

    fun saveCharacteristic(characteristics:List<Character>){
        _characteristicState.value=characteristics.toCharacteristicState()
    }
    fun changeName(text:String){
        name.value=text
        checkEnabled()
    }
    fun changeLongDescription(text:String){
        longDescription.value=text
        checkEnabled()
    }
    fun changeShortDescription(text:String){
        shortDescription.value=text
        checkEnabled()
    }
    fun changeCount(text:String){
        count.value=text
        checkEnabled()
    }
    fun changePrice(text:String){
        price.value=text
        checkEnabled()
    }

    fun changeConnectionType(state: Boolean,flag:String){
        when(state and (flag=="call")){
            true-> connectionType.value="call_and_chatting"
            false->connectionType.value="chatting"
        }
        when(state and (flag=="message")){
            true-> connectionType.value="chatting"
            false->connectionType.value="call_and_chatting"
        }
    }

    fun saveShop(id:String){
        shop.value=id
    }

    private fun checkEnabled()= updateEnabledState(name.value.isNotEmpty() and longDescription.value.isNotEmpty()
                  and shortDescription.value.isNotEmpty() and count.value.isNotEmpty() and price.value.isNotEmpty() and(
                  (_imageList.value.size!=0) or (uriSource.value.size!=0)))


    private fun updateEnabledState(state:Boolean){_enabled.value=state}

    private fun characteristicLoad()=saveCharacteristicsState().map { it?.toRequestCreate }

    private fun createRequest()= createProductRequest(count.value.toInt(), name.value,price.value,
        longDescription.value, shortDescription.value, imageList.value, connectionType.value,
        characteristicLoad(),
        category = category.value)

    fun checkUriSource():Boolean= uriSource.value.isNotEmpty()
    fun createProduct(request: ProductUpdateRequest, value: String): Flow<CreateResponseProduct> {
        Log.i("crash5",shop.value)

      return productsRepository.createProduct(value, request)
    }
    fun updateProduct(id:String,request: ProductUpdateRequest)= productsRepository.updateProduct(id, request)
    fun getCategories()=categoryRepository.getCategories()
    fun getCategoryCharacteristic(id:String)=categoryRepository.getCategoryCharacteristic(id)
    fun saveImage(resources:List<Bitmap>): Flow<SaveImageResponseData> = multimediaRepository.saveImage(imageData(resources))
    private fun imageData(resources: List<Bitmap>):MutableList<SaveImageRequestData>{

        val path= ImageMessage().encodeBitmapList(resources as ArrayList<Bitmap>)
        val images = mutableListOf<SaveImageRequestData>()
        path.map { images.add(SaveImageRequestData(it,"png")) }
        return images
    }
    fun getPriceHistory(id:String)=productsRepository.getPriceHistory(id)
    fun takeOff(id:String)=productsRepository.takeOff(id)

    private fun saveCharacteristicsState(

    ): List<CharacteristicFilterModel?> =
        UiCharacteristicState().saveFilter(_characteristicState.value.inputStateModel,
            _characteristicState.value.radioStateModel,
            _characteristicState.value.selectStateModel,
            _characteristicState.value.checkBoxStateModel
        )


    fun publishedProduct(id: String)=productsRepository.publishedProduct(id)
    fun load(status:String)=productsRepository.loadMyProducts(status)
    fun saveFile(requestData: List<SaveFileRequestData>)=
        multimediaRepository.saveFileBase64(requestData)

}