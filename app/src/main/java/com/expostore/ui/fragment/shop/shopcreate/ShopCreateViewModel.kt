package com.expostore.ui.fragment.shop.shopcreate

import com.expostore.api.pojo.addshop.AddShopRequestData
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
    private val _exist=MutableStateFlow<Boolean>(false)
    val exist=_exist.asStateFlow()
    private val _shopInfo= MutableStateFlow<ShopInfoModel>(ShopInfoModel())
            val shopInfo=_shopInfo.asStateFlow()

    fun shopEdit(requestData: AddShopRequestData,state:Boolean)=
        when(state){
            true-> repository.editShop(requestData).handleResult(_shopEdit,{navigateToProfile()})
            false->repository.shopCreate(requestData).handleResult(_shopEdit,{navigateToProfile()})
        }

    private fun navigateToProfile(){
        navigationTo(
            ShopCreateFragmentDirections.actionShopCreateToProfileFragment())
    }

    fun saveExist(exist:Boolean){
        _exist.value=exist
    }
    fun saveInfo(shopInfoModel: ShopInfoModel){
        _shopInfo.value=shopInfoModel
    }







    override fun onStart() {
        TODO("Not yet implemented")
    }


}