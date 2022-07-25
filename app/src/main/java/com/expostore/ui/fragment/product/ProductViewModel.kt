package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.data.repositories.ChatRepository
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(private val chatRepository: ChatRepository) : BaseViewModel() {
    private val _product=MutableStateFlow<ProductModel>(ProductModel())
    val product=_product.asStateFlow()


    override fun onStart() {
        TODO("Not yet implemented")
    }
    fun saveProduct(item:ProductModel){
        _product.value=item
    }
    fun navigationChat(){
          navigationTo(ProductFragmentDirections.actionProductFragmentToChatFragment())
    }

    fun navigationToAddReview(){
        navigationTo(ProductFragmentDirections.actionProductFragmentToAddReviewFragment())
    }
    fun createChat(id:String,flag:String) = chatRepository.createChat(id,flag)



}