package com.expostore.api

import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategoryadvertising.CategoryAdvertising
import com.expostore.api.pojo.getcities.City
import com.expostore.api.pojo.getcities.GetCitiesResponseData
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import retrofit2.Call
import retrofit2.http.*

interface ServerApi {

    @POST("/api/sign-in/")
    fun authorization(@Body request: SignInRequestData): Call<SignInResponseData>

    @POST("/api/confirm/create/")
    fun confirmNumber(@Body request: ConfirmNumberRequestData): Call<ConfirmNumberResponseData>

    @POST("/api/confirm/confirmed/")
    fun confirmCode(@Body request: ConfirmCodeRequestData): Call<ConfirmCodeResponseData>

    @POST("/api/sign-up/")
    fun registration(@Body request: SignUpRequestData): Call<SignUpResponseData>

    @GET("/api/cities/")
    fun getCities(@Header("Authorization") authToken: String?): Call<ArrayList<City>>

    @PUT("/api/profile/")
    fun editProfile(@Header("Authorization") authToken: String?, @Body request: EditProfileRequestData): Call<EditProfileResponseData>

    @GET("/api/selection/product/")
    fun getCategories(@Header("Authorization") authToken: String?): Call<ArrayList<Category>>

    @GET("/api/advertising/selection/")
    fun getCategoryAdvertising(@Header("Authorization") authToken: String?): Call<ArrayList<CategoryAdvertising>>
}