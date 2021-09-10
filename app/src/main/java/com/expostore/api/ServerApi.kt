package com.expostore.api

import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategory.GetCategoryResponseData
import com.expostore.api.pojo.getcategoryadvertising.CategoryAdvertising
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import retrofit2.Call
import retrofit2.http.*

interface ServerApi {
    @POST("/api/sign-in/")
    fun auth(@Body request: SignInRequestData): Call<SignInResponseData>

    @GET("/api/selection/product/")
    fun getCategories(@Header("Authorization") authToken: String?): Call<ArrayList<Category>>

    @GET("/api/advertising/selection/")
    fun getCategoryAdvertising(@Header("Authorization") authToken: String?): Call<ArrayList<CategoryAdvertising>>


}