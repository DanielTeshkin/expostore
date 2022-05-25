package com.expostore.ui.fragment.shop.shopcreate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.expostore.api.pojo.addshop.AddShopRequestData
import com.expostore.api.pojo.addshop.returnShopModel
import com.expostore.api.response.ShopResponse
import com.expostore.databinding.ShopCreateFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.InfoProfileModel
import com.expostore.ui.fragment.profile.ShopInfoModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_fragment.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ShopCreateFragment :
    BaseFragment<ShopCreateFragmentBinding>(ShopCreateFragmentBinding::inflate) {

    private  val shopCreateViewModel: ShopCreateViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            val info = bundle.getParcelable<ShopInfoModel>("name")
            val model = ShopInfoModel(
                address = info!!.address,
                name = info.name,
                shopping_center = info.shopping_center,
                floor_and_office_number = info.floor_and_office_number
            )
            shopCreateViewModel.apply {
                saveInfo(model)
                saveExist(info.info)
                subscribe(shopEdit){handleResult(it)}
                subscribe(navigation){navigateSafety(it)}
            }
        }

    }

    private fun handleResult(state: ResponseState<ShopResponse>) {
        when(state){
            is ResponseState.Error-> state.throwable.message?.let { handleError(it) }
            is ResponseState.Loading ->binding.progressBar7.visibility=View.VISIBLE
            is ResponseState.Success -> binding.progressBar7.visibility=View.GONE
        }

    }

    override fun onStart() {
        super.onStart()
         init()
    }

    fun init(){
        state {
            fillFields()
        }
        binding.apply {

            btnSave.click {
                val request = returnShopModel(
                    binding.etShopName.text.toString(),
                    binding.etShopAddress.text.toString(),
                    binding.etShopShoppingCenter.text.toString(),
                    binding.etShopOffice.text.toString()
                )
                state {
                    editRequest(request)

                }
            }
        }
    }


    private suspend fun fillFields(){
        shopCreateViewModel.shopInfo.collect {
            binding.apply {
                etShopAddress.setText(it.address)
                etShopName.setText(it.name)
                etShopShoppingCenter.setText(it.shopping_center)
                 etShopOffice.setText(it.floor_and_office_number)
            } }
    }

    private suspend fun editRequest( requestData: AddShopRequestData){
        shopCreateViewModel.apply {
            exist.collect{
                shopEdit(requestData,it)
            }
        }
    }

}