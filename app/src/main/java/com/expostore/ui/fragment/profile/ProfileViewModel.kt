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
import com.expostore.data.remote.api.pojo.addshop.AddShopRequestData

import com.expostore.data.remote.api.pojo.getprofile.EditProfileRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.response.EditResponseProfile
import com.expostore.data.remote.api.response.ShopResponse
import com.expostore.data.repositories.MultimediaRepository
import com.expostore.data.repositories.ProfileRepository
import com.expostore.data.repositories.ShopRepository
import com.expostore.model.profile.ProfileModel

import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.chats.toBase64
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository,
                                           private val shopInteractor: ShopRepository,
                                           private val multimediaRepository: MultimediaRepository) : BaseViewModel() {
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
   private val _shop =MutableStateFlow(ProfileModel.ShopModel())
    val shop =_shop.asStateFlow()
    private val _shopAvatar=MutableStateFlow<String>("")
    val shopAvatar=_shopAvatar.asStateFlow()
    private val _name=MutableStateFlow<String>("")
    val name=_name.asStateFlow()
    private val _username=MutableStateFlow<String>("")
    val username=_username.asStateFlow()
    private val _profileInfo=MutableStateFlow<ProfileModel>(ProfileModel())
            val profileInfo=_profileInfo.asStateFlow()

    private val _tag=MutableStateFlow<String>("")
    override fun onStart() {
       loadProfile()
    }

    fun loadProfile(){
        profileRepository.getProfile().handleResult(_profile)
    }

    fun updateTag(tag:String){
        _tag.value=tag
    }
    fun save(model:ProfileModel){

        if(model.shop?.id!=""){
            Log.i("id",model.shop?.id?:"")
            _title.value="Редактировать магазин"
        _exist.value=true
        }
        _profileInfo.value=model

    }

    fun updateAvatar(context: Context, uri: Uri) {
        saveImage(context, uri)
        Log.i("show", uri.toString())
        viewModelScope.launch {
            _save.collect {
                when (it) {
                    is ResponseState.Success -> {
                        Log.i("show", it.item.id[0])
                        update(it.item.id[0], _tag.value)
                    }
                    is ResponseState.Error -> it.throwable.message?.let { it1 ->
                        Log.i("error",
                            it1)
                    }
                    else -> {}
                } } }
    }

    private fun update(model:String,tag: String?){
        if(tag=="avatar"){
        profileRepository.patchProfile(EditProfileRequest(avatar = model)).handleResult(_changeProfile)
            viewModelScope.launch {
                _changeProfile.collect {
                    when (it) {
                        is ResponseState.Success -> loadProfile()
                    } } }

        }
        else{shopInteractor.editShop(AddShopRequestData(image = model)).handleResult(_shopEdit) }
        viewModelScope.launch {
            _shopEdit.collect {
                when (it) {
                    is ResponseState.Success -> loadProfile()
                } } }
    }


       fun removeToken(){
           viewModelScope.launch {
               withContext(Dispatchers.IO) {
                   profileRepository.deleteAll() }
           }
       }


    private fun saveImage(context:Context,uri: Uri){
        Glide.with(context).asBitmap().load(uri).into(object :
            CustomTarget<Bitmap>(720,720){
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                 val image =resource.toBase64()
                val list= listOf(SaveImageRequestData(image!!,"png"))

                multimediaRepository.saveImage(list).handleResult(_save)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
            }

        })

    }

    fun navigateEditProfile(){
        val model=_profileInfo.value
        val result = InfoProfileModel(
            model.firstName!!, model.lastName!!, model.city!!,
            model.email!!
        )
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(result))
    }
    fun navigateMyProduct(){
        when(exist.value){
        true ->navigationTo(ProfileFragmentDirections.actionProfileFragmentToMyProductsFragment())
            else -> navigateShop()
        }
    }
    fun navigateReviews(flag:String){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToReviewsFragment(flag))
    }


    fun navigateShop(){
        val shop=_shop.value
        val info = ShopInfoModel(shop!!.name, shop.shoppingCenter, shop.address,shop.floor_and_office_number, exist.value,shop.phone)
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToShopCreate(info))
    }

    fun navigationToSupport(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToSupportFragment())
    }

    fun navigateToWeb(){
        navigationTo(ProfileFragmentDirections.actionProfileFragmentToWebViewFragment("https://expostore.ru/"))
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