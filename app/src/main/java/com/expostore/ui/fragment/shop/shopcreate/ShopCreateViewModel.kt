package com.expostore.ui.fragment.shop.shopcreate

import com.expostore.api.pojo.addshop.AddShopRequestData
import com.expostore.api.pojo.addshop.returnShopModel
import com.expostore.api.response.ShopResponse
import com.expostore.data.repositories.ShopRepository
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.profile.ShopInfoModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class ShopCreateViewModel @Inject constructor(private val repository: ShopRepository) : BaseViewModel() {
    private val _shopEdit= MutableSharedFlow<ResponseState<ShopResponse>>()
    val shopEdit=_shopEdit.asSharedFlow()
    private val _exist=MutableStateFlow(false)
    val exist=_exist.asStateFlow()
    private val _shopInfo= MutableStateFlow(ShopInfoModel())
    val shopInfo=_shopInfo.asStateFlow()
    private val name=MutableStateFlow("")
    private val location=MutableStateFlow("")
    private val office= MutableStateFlow("")
    private val shoppingCenter= MutableStateFlow("")
    private val phone= MutableStateFlow("")
    private val _enabledState= MutableStateFlow(false)
    val enabledState=_enabledState.asStateFlow()

    fun shopEdit() {
        val request = returnShopModel(
            name.value,
            location.value,
            shoppingCenter.value,
            office.value,
            phone.value
        )
        when (exist.value) {
            true -> repository.editShop(request)
                .handleResult(_shopEdit, { navigateToProfile() })
            false -> repository.shopCreate(request)
                .handleResult(_shopEdit, { navigateToProfile() })
        }
    }

    private fun navigateToProfile() = navigationTo(ShopCreateFragmentDirections.actionShopCreateToProfileFragment())


    fun updateName(mean:String){
        name.value=mean
        checkStateFields()
    }
    fun updateLocation(mean: String){
        location.value=mean
        checkStateFields()
    }
    fun updatePhone(mean: String){ phone.value=mean }
    fun updateOffice(mean: String) { office.value=mean }
    fun updateShoppingCenter(mean: String){ shoppingCenter.value=mean }

  private  fun checkStateFields(){
        _enabledState.value = name.value.isNotEmpty()&&location.value.isNotEmpty()
    }

    fun saveInfo(shopInfoModel: ShopInfoModel){
        _exist.value=shopInfoModel.info
        _shopInfo.value=shopInfoModel
        name.value=shopInfoModel.name
        shoppingCenter.value=shopInfoModel.shopping_center
        location.value=shopInfoModel.phone
        office.value=shopInfoModel.floor_and_office_number
        phone.value=shopInfoModel.phone

    }







    override fun onStart() {
        TODO("Not yet implemented")
    }


}