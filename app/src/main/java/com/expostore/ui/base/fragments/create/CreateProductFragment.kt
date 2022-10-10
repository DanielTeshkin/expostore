package com.expostore.ui.base.fragments.create

import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.response.CreateResponseProduct
import com.expostore.data.remote.api.response.ProductResponse
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.vms.CreatorProductViewModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener


abstract class CreateProductFragment() : BaseCreatorFragment<AddProductFragmentBinding,
        CreateResponseProduct, ProductResponse, ProductUpdateRequest,ProductModel>(AddProductFragmentBinding::inflate) {
    override val categoriesLayout: LinearLayout get()= binding.llAddProductCategory
    override val characteristicsLayout: LinearLayout get()= binding.llAddProductCharacteristic
    override val rvCharacteristics: RecyclerView get()= binding.rvProductCharacteristic
    override val filter: String  = "other"
    override val item: ProductModel?= null
    abstract override val viewModel: CreatorProductViewModel
    override val call: CheckBox get()= binding.call
    override val message: CheckBox get() = binding.message
    override val connectionLayout: LinearLayout get() = binding.llAddProductConnections
    override val btnCancel: Button get() = binding.btnCancel
    override val btnDraft: Button get() = binding.btnSaveDraft
    override val btnSave: Button get() = binding.btnSave
    override val rvImages: RecyclerView get()= binding.rvProductImages

    override fun loadSaveInformation(productModel:ProductModel) {
        saveItem(productModel.id)
        Log.i("log",productModel.id)
        binding.apply {
            val images = productModel.images
            viewModel.saveImages(images.map { it.id })
            list.addAll(images.map { it.file })
            addProductTitle.text = "Редактировать продукт"
            etProductName.setText(productModel.name)
            viewModel.changeName(productModel.name)
            etProductCount.setText(productModel.count.toString())
            etProductPrice.setText(productModel.price)
            etProductDescription.setText(productModel.shortDescription)
            etLongDescription.setText(productModel.longDescription)
            viewModel.saveCategory(productModel.category?.id ?: "")
            viewModel.saveCharacteristic(productModel.characteristics)

        }
    }

    override fun setTextChangeListeners() {
        binding.apply {
            etProductName.addTextChangedListener {viewModel.changeName(it.toString()) }
            etProductPrice.addTextChangedListener {viewModel.changePrice(it.toString()) }
            etLongDescription.addTextChangedListener {viewModel.changeDescription(it.toString())}
            etProductCount.addTextChangedListener {viewModel.changeCount(it.toString())}
            etProductDescription.addTextChangedListener { viewModel.changeShortDescription(it.toString()) }
        }
    }

    override fun handleNewItem(model: CreateResponseProduct) {
        TODO("Not yet implemented")
    }

    override fun handlePublic(model: ProductResponse) {
        TODO("Not yet implemented")
    }


}