package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.ImageModel
import com.expostore.model.profile.ProfileModel
import com.expostore.model.toModel

@Entity(tableName = "profile")
class ProfileDao (@PrimaryKey
                  @ColumnInfo(name = "id")
                  val id: String = "",
                  @ColumnInfo(name = "shop")
                  val shop: ProfileModel.ShopModel? = ProfileModel.ShopModel(),
                  @ColumnInfo(name = "city")
                  val city: String? ="",
                  @ColumnInfo(name = "last_name")
                  val lastName: String? = "",
                  @ColumnInfo(name = "avatar")
                  val avatar: ImageModel? = ImageModel(),
                  @ColumnInfo(name = "isEnabledPushNotify")
                  val isEnabledPushNotify: Boolean? = false,
                  @ColumnInfo(name = "causeBlocked")
                  val causeBlocked: String? = "",
                  @ColumnInfo(name = " isBlocked")
                  val isBlocked: Boolean? = false,
                  @ColumnInfo(name = "patronymic")
                  val patronymic: String? = "",
                  @ColumnInfo(name = "first_name")
                  val firstName: String? = "",
                  @ColumnInfo(name = "isEnabledNotifyEmail")
                  val isEnabledNotifyEmail: Boolean? = false,
                  @ColumnInfo(name = "email")
                  val email: String? = "",
                  @ColumnInfo(name = "pushToken")
                  val pushToken: String? = "",
                  @ColumnInfo(name = "username")
                  val username: String = "",
                  @ColumnInfo(name = "balance", defaultValue = 0.0.toString())
                  val balance: Double = 0.0

)


val ProfileModel.toDao:ProfileDao
get() = ProfileDao(id?:"",shop, city, lastName, avatar, isEnabledPushNotify, causeBlocked, isBlocked, patronymic, firstName, isEnabledNotifyEmail, email, pushToken, username,balance)

val GetProfileResponseData.toDao
    get() = ProfileDao(id?:"",ProfileModel.ShopModel(   shop?.id?:"",
        shop?.owner ?: "",
        shop?.image?.toModel ?: ImageModel(),
        shop?.address ?: "",
        shop?.name ?: "",
        shop?.lat ?: 0.0,
        shop?.lng ?: 0.0,
        shop?.shoppingCenter ?: "",
        shop?.floor_and_office_number?:"",shop?.phone?:""), city, lastName, avatar?.toModel?:ImageModel(), isEnabledPushNotify, causeBlocked, isBlocked, patronymic, firstName, isEnabledNotifyEmail, email, pushToken, username?:"",balance)