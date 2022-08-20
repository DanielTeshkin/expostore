package com.expostore.api.pojo.getprofile


import com.google.gson.annotations.SerializedName

data class EditProfileRequest(  @SerializedName("username"                ) var username             : String?  = null,
                                @SerializedName("last_name"               ) var lastName             : String?  = null,
                                 @SerializedName("first_name" ) var firstName            : String?  = null,
                                 @SerializedName("patronymic" ) var patronymic           : String?  = null,
                                 @SerializedName("email" ) var email                : String?  = null,
                                 @SerializedName("city"  ) var city                 : String?     = null,
                                @SerializedName("avatar") val avatar:String?=null,
                                 @SerializedName("push_token" ) var pushToken: String?  = null)
