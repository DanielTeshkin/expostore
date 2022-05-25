package com.expostore.ui.fragment.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.api.pojo.addshop.AddShopRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getprofile.EditProfileRequest
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.response.EditResponseProfile
import com.expostore.api.response.ShopResponse
import com.expostore.model.profile.ProfileModel
import com.expostore.model.profile.name
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.toBase64
import com.expostore.ui.fragment.shop.shopcreate.InteractorShopCreate
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository,private val shopInteractor: InteractorShopCreate) : BaseViewModel() {
         private val _profile=MutableSharedFlow<ResponseState<ProfileModel>>()
    val profile=_profile.asSharedFlow()
    private val _title=MutableStateFlow<String>("Создать магазин")
    val title=_title.asStateFlow()
    private val _exist=MutableStateFlow(false)
    val exist=_exist.asStateFlow()
    private val _save=MutableSharedFlow<ResponseState<SaveImageResponseData>>()
    val save=_save.asSharedFlow()
    private val _changeProfile= MutableSharedFlow<ResponseState<EditResponseProfile>>()
    val change=_changeProfile.asSharedFlow()
    private val _shopEdit= MutableSharedFlow<ResponseState<ShopResponse>>()
    val shopEdit=_shopEdit.asSharedFlow()
   private val _myAvatar=MutableStateFlow<String>("")
    val myAvatar=_myAvatar.asStateFlow()

    private val _shopAvatar=MutableStateFlow<String>("")
    val shopAvatar=_shopAvatar.asStateFlow()
    private val _name=MutableStateFlow<String>("")
    val name=_name.asStateFlow()
    private val _username=MutableStateFlow<String>("")
    val username=_username.asStateFlow()


    fun loadProfile(){
        profileRepository.getProfile().handleResult(_profile)
    }
    fun save(model:ProfileModel){
        if(model.shop!=null){
            _title.value="Редактировать магазин"
        _exist.value=true
        }
        _myAvatar.value= model.avatar?.file ?: ""
        _shopAvatar.value= model.shop?.image?.file ?: ""
        _name.value=model.name()
        _username.value=model.username
    }
    fun updateAvatar(context: Context, uri: Uri, tag: String?) {
        saveImage(context, uri)
        Log.i("show", uri.toString())
        viewModelScope.launch {
            _save.collect {
                when (it) {
                    is ResponseState.Success -> {
                        Log.i("show", it.item.id)
                        update(it.item, tag)
                    }
                    is ResponseState.Error -> it.throwable.message?.let { it1 ->
                        Log.i("error",
                            it1)
                    }
                } } }
    }

    private fun update(model:SaveImageResponseData,tag: String?){
        if(tag=="avatar"){
        profileRepository.patchProfile(EditProfileRequest(avatar = model.id)).handleResult(_changeProfile)
            viewModelScope.launch {
                _changeProfile.collect {
                    when (it) {
                        is ResponseState.Success -> loadProfile()
                    } } }

        }
        else{shopInteractor.editShop(AddShopRequestData(image = model.id)).handleResult(_shopEdit) }
        viewModelScope.launch {
            _shopEdit.collect {
                when (it) {
                    is ResponseState.Success -> loadProfile()
                } } }
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }





    private fun saveImage(context:Context,uri: Uri){

        Glide.with(context).asBitmap().load(uri).into(object :
            CustomTarget<Bitmap>(720,720){
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                 val image =resource.toBase64()
                profileRepository.saveImage(SaveImageRequestData(image!!,"png")).handleResult(_save)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })

    }

    fun navigateEditProfile(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
    }
    fun navigateMyProduct(){
        when(exist.value){
        true ->navigationTo(ProfileFragmentDirections.actionProfileFragmentToMyProductsFragment())
            else -> navigateShop()
        }
    }
    fun navigateReviews(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToReviewsFragment())
    }


    fun navigateShop(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToShopCreate())
    }

    fun navigationToSupport(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToSupportFragment())
    }

    fun navigateToWeb(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToWebViewFragment())
    }
    fun navigateToTender(){
        when(exist.value){
            true ->navigationTo(ProfileFragmentDirections.actionProfileFragmentToMyTenders())
            else -> navigateShop()
        }
    }
    fun navigateToLogin(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

}