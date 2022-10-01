package com.expostore.ui.fragment.product.addproduct

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.fragments.create.BaseCreatorFragment
import com.expostore.ui.base.fragments.create.CreateProductFragment
import com.expostore.ui.controllers.BaseCreatorController
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.chats.listPath
import com.expostore.ui.fragment.profile.profile_edit.click
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment :  CreateProductFragment() {
    override val viewModel: AddProductViewModel by viewModels()
    override val item: ProductModel?
        get() = AddProductFragmentArgs.fromBundle(requireArguments()).product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installResultListeners()
        installNetworkObserverStateUi()
    }

    override fun onStart() {
        super.onStart()
       binding.apply {
           presentionBtn.click {
               viewModel.updateFlag("presentation")
               openFilesStorage()
           }
          instructionBtn.click {
              viewModel.updateFlag("instruction")
              openFilesStorage()
          }
       }
    }

    private fun openFilesStorage()= resultLauncher.launch(FileStorage(requireContext()).openStorageSingle())

    private fun installNetworkObserverStateUi() {
        viewModel.apply {
            subscribe(files){ state -> handleState(state){viewModel.addFiles(it.files)} }
        }
    }

     fun installResultListeners() {
         setFragmentResultListener("shop") { _, bundle ->
                    val result = bundle.getString("id")
                    if (result != null) {
                        viewModel.saveShopId(result)
                    }
                }
     }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("dggg","ddd")
            if (result.resultCode == Activity.RESULT_OK) {
                val uri=result.data?.data
                     when(viewModel.flag.value){
                         "presentation"-> {
                             viewModel.savePresentation(uri ?: Uri.parse(""))
                             binding.addPresentation.text= uri.toString()
                         }

                         "instruction"->{
                             viewModel.saveInstruction(uri?: Uri.parse(""))
                             binding.addInstruction.text= uri.toString()
                         }
                     } }

        }



}
    fun EditText.stroke():String = text.toString()
