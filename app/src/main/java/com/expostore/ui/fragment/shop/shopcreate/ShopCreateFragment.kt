package com.expostore.ui.fragment.shop.shopcreate

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.databinding.ShopCreateFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Load

import com.expostore.ui.fragment.profile.ShopInfoModel
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ShopCreateFragment :
    BaseFragment<ShopCreateFragmentBinding>(ShopCreateFragmentBinding::inflate) {
    private  val shopCreateViewModel: ShopCreateViewModel by viewModels()
    val load:Load ={ loading(it) }
    override var isBottomNavViewVisible: Boolean=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            val info = bundle.getParcelable<ShopInfoModel>("name")
            val model = ShopInfoModel(
                address = info!!.address,
                name = info.name,
                shopping_center = info.shopping_center,
                floor_and_office_number = info.floor_and_office_number,
                info = info.info,
                phone = info.phone
            )
            shopCreateViewModel.apply { saveInfo(model) }

        }

    }
    override fun onStart() {
        super.onStart()
        shopCreateViewModel.apply {
            subscribe(shopEdit){handleLoadingState(it,loader =  load)}
            subscribe(navigation){navigateSafety(it)}
        }
        init()

    }

    fun loading(state:Boolean){
        binding.progressBar7.isVisible=state
    }

    fun init(){
        state { fillFields()}
        binding.apply {
            state { shopCreateViewModel.enabledState.collect { btnSave.isEnabled=it } }
            shopCreateViewModel.apply {
                etShopOffice.addTextChangedListener {updateOffice(it.toString())}
                etShopName.addTextChangedListener { updateName(it.toString()) }
                etShopShoppingCenter.addTextChangedListener { updateShoppingCenter(it.toString()) }
                etShopAddress.addTextChangedListener { updateLocation(it.toString()) }
                etShopPhone.addTextChangedListener { updatePhone(it.toString()) }
            }
            btnSave.click {shopCreateViewModel.shopEdit()}
        }
    }

    private suspend fun fillFields(){
        shopCreateViewModel.shopInfo.collect {
            binding.apply {
                etShopAddress.setText(it.address)
                etShopName.setText(it.name)
                etShopShoppingCenter.setText(it.shopping_center)
                etShopOffice.setText(it.floor_and_office_number)
            }
        }
    }



}