package com.expostore.ui.fragment.product

import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.request.ProductUpdateRequest
import com.expostore.data.repositories.*
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.ui.general.UiCharacteristicState
import com.expostore.ui.general.CheckBoxStateModel
import com.expostore.ui.general.InputStateModel
import com.expostore.ui.general.RadioStateModel
import com.expostore.ui.general.SelectStateModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ProductInteractor @Inject constructor(private val productsRepository: ProductsRepository,
                                            private val categoryRepository: CategoryRepository,
                                            private val profileRepository: ProfileRepository,
                                            private val multimediaRepository: MultimediaRepository,
                                            private val chatRepository: ChatRepository)

{
    private val filterInputList= MutableStateFlow(InputStateModel(hashMapOf()))
    private val filterSelectList= MutableStateFlow(SelectStateModel(hashMapOf()))
    private val filterRadioList= MutableStateFlow(RadioStateModel(hashMapOf()))
    private val filterCheckBox= MutableStateFlow(CheckBoxStateModel(hashMapOf()))

    fun addFilterInput(left:String,right:String,name:String){
        filterInputList.value.state[name] = Pair(left,right)
    }

    fun addFilterSelect(name: String,list:List<String>){
        filterSelectList.value.state[name] = list
    }
    fun addFilterRadio(id:String,name: String){
        filterRadioList.value.state[name]=id
    }
    fun addFilterCheckbox(name: String,check:Boolean){
        filterCheckBox.value.state[name]=check
    }



    fun createProduct(id:String,request: ProductUpdateRequest) = productsRepository.createProduct(id, request)
    fun updateProduct(id:String,request: ProductUpdateRequest)= productsRepository.updateProduct(id, request)
    fun putToDraft(id:String,request: ProductUpdateRequest)= productsRepository.putToDraft(id,request)
    fun getCategories()=categoryRepository.getCategories()
    fun getCategoryCharacteristic(id:String)=categoryRepository.getCategoryCharacteristic(id)

    fun saveImage(saveImageRequestData: List<SaveImageRequestData>)=multimediaRepository.saveImage(saveImageRequestData)

    fun takeOff(id:String)=productsRepository.takeOff(id)

    fun saveCharacteristicsState(): List<CharacteristicFilterModel> =
        UiCharacteristicState().saveFilter(
            filterInputList.value,
            filterRadioList.value,
            filterSelectList.value,
            filterCheckBox.value
        )


    suspend fun getProfile()=profileRepository.getProfileRemote()

    fun publishedProduct(id: String)=productsRepository.publishedProduct(id)

    fun load(status:String)=productsRepository.load(status)

    fun createChat(id: String)=chatRepository.createChat(id,"product")

}