package com.expostore.api.pojo.getprofile


import com.google.gson.annotations.SerializedName

data class EditProfileRequest(  @SerializedName("username"                ) var username             : String?  = null,
                                 @SerializedName("is_enabled_push_notify"  ) var isEnabledPushNotify  : Boolean? =  true,
                                 @SerializedName("is_enabled_notify_email" ) var isEnabledNotifyEmail : Boolean? =  true,
                                 @SerializedName("is_blocked"              ) var isBlocked            : Boolean? = false,
                                 @SerializedName("cause_blocked"           ) var causeBlocked         : String?  = null,
                                 @SerializedName("last_name"               ) var lastName             : String?  = null,
                                 @SerializedName("first_name" ) var firstName            : String?  = null,
                                 @SerializedName("patronymic" ) var patronymic           : String?  = null,
                                 @SerializedName("email" ) var email                : String?  = null,
                                 @SerializedName("city"  ) var city                 : String?     = null,
                                @SerializedName("avatar") val avatar:String?=null,
                                 @SerializedName("push_token" ) var pushToken: String?  = null)
