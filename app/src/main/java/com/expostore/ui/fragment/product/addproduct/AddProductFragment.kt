package com.expostore.ui.fragment.product.addproduct

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseCreatorFragment
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.controllers.AddProductController
import com.expostore.ui.fragment.product.addproduct.adapter.ProductCreateImageAdapter
import com.expostore.ui.fragment.product.addproduct.adapter.utils.ImagesEdit
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicState
import com.expostore.ui.general.CharacteristicsStateModel
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AddProductFragment : BaseCreatorFragment<AddProductFragmentBinding>(AddProductFragmentBinding::inflate) {
        override val viewModel: AddProductViewModel by viewModels()
   override val controller :AddProductController by lazy {
         AddProductController(requireContext(),binding,
             addPhoto = {addPhoto()},
             saveFile = {viewModel.saveFile(it)},
             saveAction = {viewModel.loadImages(it)},
             loadCharacteristics = {viewModel.getCharacteristics(it)},
             action = {status,request->viewModel.createOrUpdate(request,status)}

             )
     }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installSetResultListeners()
        installNetworkObserverStateUi()
     }

    override fun onStart() {
        super.onStart()
        controller.initAdapter()
    }

    private fun installNetworkObserverStateUi(){
      viewModel.apply {
            subscribe(addProduct) { handleState(it) }
            subscribe(saveFile) { handleState(it) { controller.apply { addFiles(it.files) } } }
            subscribe(productPublic) { handleState(it) }
      }
    }
    private fun installSetResultListeners(){
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<ProductModel>("product")
            if (result != null) {
                viewModel.saveProductInformation(result)
                controller.init(result) } }
        setFragmentResultListener("shop") { _, bundle ->
            val result = bundle.getString("id")
            if (result != null) {
              viewModel.saveShopId(result)
            }
        }
    }

    override fun handleImages(images: SaveImageResponseData) {
        controller.addImages(images.id)
        controller.update()
    }

}
    fun EditText.stroke():String = text.toString()
