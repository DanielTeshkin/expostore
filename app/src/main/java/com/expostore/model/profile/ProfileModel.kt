package com.expostore.model.profile

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.data.local.db.enities.ProfileDao
import com.expostore.model.ImageModel
import com.expostore.model.toModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(
    val id: String? = "",
    val shop: ShopModel? = ShopModel(),
    val city: String? ="",
    val lastName: String? = "",
    val avatar: ImageModel? =ImageModel() ,
    val isEnabledPushNotify: Boolean? = false,
    val causeBlocked: String? = "",
    val isBlocked: Boolean? = false,
    val patronymic: String? = "",
    val firstName: String? = "",
    val isEnabledNotifyEmail: Boolean? = false,
    val email: String? = "",
    val pushToken: String? = "",
    val username: String = "",
    val balance:Double=0.0
): Parcelable {
    @Parcelize
    data class ShopModel(
        val id: String="",
        val owner: String = "",
        val image: ImageModel? = ImageModel(),
        val address: String = "",
        val name: String = "",
        val lat: Double = 0.0,
        val lng: Double = 0.0,
        val shoppingCenter: String = "",
        val  floor_and_office_number :String= "",
        val phone:String =""
    ) :Parcelable
}

fun ProfileModel.name():String= "$firstName $lastName"




val GetProfileResponseData.toModel: ProfileModel
    get() = ProfileModel(
        id ?: "",
        ProfileModel.ShopModel(
            shop?.id?:"",
            shop?.owner ?: "",
            shop?.image?.toModel ?: ImageModel(),
            shop?.address ?: "",
            shop?.name ?: "",
            shop?.lat ?: 0.0,
            shop?.lng ?: 0.0,
            shop?.shoppingCenter ?: "",
            shop?.floor_and_office_number?:"",
            shop?.phone?:""
        ),
        city ?: "",
        lastName ?: "",
        avatar?.toModel,
        isEnabledPushNotify ?: false,
        causeBlocked ?: "",
        isBlocked ?: false,
        patronymic ?: "",

        firstName ?: "",
        isEnabledNotifyEmail ?: false,
        email ?: "",
        pushToken ?: "",
        username ?: "",
        balance
    )
val ProfileDao.toModel:ProfileModel
get() = ProfileModel(id,shop, city, lastName, avatar, isEnabledPushNotify, causeBlocked, isBlocked, patronymic, firstName, isEnabledNotifyEmail, email, pushToken, username,balance)
