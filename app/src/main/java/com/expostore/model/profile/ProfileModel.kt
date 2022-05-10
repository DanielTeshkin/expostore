package com.expostore.model.profile

import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.ImageModel
import com.expostore.model.toModel

data class ProfileModel(
    val shop: ShopModel? = ShopModel(),
    val city: String? ="",
    val lastName: String? = "",
    val avatar: String? = "",
    val isEnabledPushNotify: Boolean? = false,
    val causeBlocked: String? = "",
    val isBlocked: Boolean? = false,
    val patronymic: String? = "",
    val id: String? = "",
    val firstName: String? = "",
    val isEnabledNotifyEmail: Boolean? = false,
    val email: String? = "",
    val pushToken: String? = "",
    val username: String? = ""
) {
    data class ShopModel(
        val owner: String = "",
        val image: ImageModel = ImageModel(),
        val address: String = "",
        val name: String = "",
        val lat: Double = 0.0,
        val lng: Double = 0.0,
        val shoppingCenter: String = ""
    )
}

val GetProfileResponseData.toModel: ProfileModel
    get() = ProfileModel(
        ProfileModel.ShopModel(
            shop?.owner ?: "",
            shop?.image?.toModel ?: ImageModel(),
            shop?.address ?: "",
            shop?.name ?: "",
            shop?.lat ?: 0.0,
            shop?.lng ?: 0.0,
            shop?.shoppingCenter ?: ""
        ),
        city ?: "",
        lastName ?: "",
        avatar ?: "",
        isEnabledPushNotify ?: false,
        causeBlocked ?: "",
        isBlocked ?: false,
        patronymic ?: "",
        id ?: "",
        firstName ?: "",
        isEnabledNotifyEmail ?: false,
        email ?: "",
        pushToken ?: "",
        username ?: ""
    )
