package com.expostore.ui.fragment.product.addproduct

import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.request.ProductUpdateRequest
import com.expostore.data.repositories.CategoryRepository
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.ProductsRepository
import com.expostore.model.category.CharacteristicFilterModel
import com.expostore.ui.base.UiCharacteristicState
import com.expostore.ui.fragment.search.filter.models.CheckBoxStateModel
import com.expostore.ui.fragment.search.filter.models.InputStateModel
import com.expostore.ui.fragment.search.filter.models.RadioStateModel
import com.expostore.ui.fragment.search.filter.models.SelectStateModel
import javax.inject.Inject

class AddProductInteractor @Inject constructor( private val productsRepository: ProductsRepository,
                                                private val  categoryRepository: CategoryRepository,
private val multimediaRepository: MultimediaRepository) {
    fun createProduct(id:String,request: ProductUpdateRequest) = productsRepository.createProduct(id, request)
    fun updateProduct(id:String,request: ProductUpdateRequest)= productsRepository.updateProduct(id, request)
    fun putToDraft(id:String,request: ProductUpdateRequest)= productsRepository.putToDraft(id,request)
    fun getCategories()=categoryRepository.getCategories()
    fun getCategoryCharacteristic(id:String)=categoryRepository.getCategoryCharacteristic(id)
    fun saveImage(saveImageRequestData: List<SaveImageRequestData>)=multimediaRepository.saveImage(saveImageRequestData)
    fun saveCharacteristicsState(inputStateModel: InputStateModel,
                                 radioStateModel: RadioStateModel,
                                 selectStateModel: SelectStateModel,
                                 checkBoxStateModel: CheckBoxStateModel): List<CharacteristicFilterModel> =
        UiCharacteristicState().saveFilter(
            inputStateModel,
            radioStateModel,
            selectStateModel,
            checkBoxStateModel
        )

}