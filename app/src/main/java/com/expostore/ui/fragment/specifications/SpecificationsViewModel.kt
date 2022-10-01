package com.expostore.ui.fragment.specifications

import com.expostore.data.repositories.CategoryRepository
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.vms.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
@HiltViewModel
class SpecificationsViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : BaseViewModel() {

    private val selectCategory= MutableStateFlow<String>("")
    private val _categories= MutableSharedFlow<ResponseState<List<ProductCategoryModel>>>()
    val categories=_categories.asSharedFlow()
    private val flag= MutableStateFlow<String>("")
    private  val flagPage= MutableStateFlow<String>("")

    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun getCategories(){
        categoryRepository.getCategories().handleResult(_categories)
    }
        fun saveFlag(name:String){
            flag.value=name
        }

    fun saveSelect(id:String){
        selectCategory.value=id
    }

    fun navigate(){
        when (flag.value) {
            "" -> navigationTo(SpecificationsFragmentDirections.actionSpecificationsFragmentToAddProductFragment())
            "filter" -> navigationTo(SpecificationsFragmentDirections.actionSpecificationsFragmentToSearchFilterFragment())
            "tender" -> navigationTo(SpecificationsFragmentDirections.actionSpecificationsFragmentToTenderCreateFragment())
        }
    }




}