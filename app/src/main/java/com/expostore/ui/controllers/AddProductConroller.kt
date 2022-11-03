package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.request.ProductUpdateRequest
import com.expostore.data.remote.api.request.createProductRequest
import com.expostore.databinding.AddProductFragmentBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel

import com.expostore.ui.base.ConditionProcessor
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicsStateModel
import kotlinx.coroutines.flow.MutableStateFlow

class AddProductController(context: Context,
                           private val binding:AddProductFragmentBinding,
                           private val saveAction:(List<Bitmap>)->Unit,
                           private val saveFile:(List<SaveFileRequestData>)->Unit,
                           private val loadCharacteristics:(String)->Unit,
                           private val  addPhoto:()->Unit,
                           private val action: (ProductUpdateRequest, String) -> Unit,
                           override val fieldCategory: LinearLayout =binding.llAddProductCategory,
                           override var fieldCharacteristic: LinearLayout=binding.llAddProductCharacteristic,
                           override val recycleViewCharacteristics: RecyclerView=binding.rvProductCharacteristic

) : BaseCreatorController(context,addPhoto,  loadCharacteristics)

