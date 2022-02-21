package com.expostore.api

import com.expostore.api.pojo.addreview.AddReviewRequestData
import com.expostore.api.pojo.addreview.AddReviewResponseData
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.api.pojo.createtender.CreateTenderRequestData
import com.expostore.api.pojo.createtender.CreateTenderResponseData
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategoryadvertising.CategoryAdvertising
import com.expostore.api.pojo.getchats.Chat
import com.expostore.api.pojo.getcities.City
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.pojo.getlistproduct.GetListProductResponseData
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.api.pojo.getreviews.ReviewsResponseData
import com.expostore.api.pojo.getshop.GetShopResponseData
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ServerApi {

    @POST("/api/sign-in/")
    fun authorization(@Body request: SignInRequestData): Response<SignInResponseData>

    @POST("/api/confirm/create/")
    fun confirmNumber(@Body request: ConfirmNumberRequestData): Response<ConfirmNumberResponseData>

    @POST("/api/confirm/confirmed/")
    fun confirmCode(@Body request: ConfirmCodeRequestData): Response<ConfirmCodeResponseData>

    @POST("/api/sign-up/")
    fun registration(@Body request: SignUpRequestData): Response<SignUpResponseData>

    @GET("/api/cities/")
    fun getCities(@Header("Authorization") authToken: String?): Response<List<City>>

    @PUT("/api/profile/")
    fun editProfile(@Header("Authorization") authToken: String?, @Body request: EditProfileRequestData): Response<EditProfileResponseData>

    @GET("/api/selection/product/")
    fun getCategories(@Header("Authorization") authToken: String?): Response<List<Category>>

    @GET("/api/advertising/selection/")
    fun getCategoryAdvertising(@Header("Authorization") authToken: String?): Response<List<CategoryAdvertising>>

    //TODO изменить
    @POST("/api/product/{id}/elected/select/")
    fun selectFavorite(@Header("Authorization") authToken: String?, @Path("id") id: String): Response<SelectFavoriteResponseData>

    @GET("/api/product/elected/list/")
    fun getFavoritesList(@Header("Authorization") authToken: String?): Response<List<GetFavoritesListResponseData>>

    @POST("/api/tender/create/")
    fun createTender(@Header("Authorization") authToken: String?, @Body requestData: CreateTenderRequestData): Response<CreateTenderResponseData>

    @POST("/api/image/save/")
    fun saveImage(@Header("Authorization") authToken: String?,@Body request: SaveImageRequestData): Response<SaveImageResponseData>

    @GET("/api/product/category/")
    fun getProductCategory(@Header("Authorization") authToken: String?): Response<List<ProductCategory>>

    @GET("/api/tender/")
    fun getTenders(@Header("Authorization") authToken: String?): Response<List<Tender>>

    @GET("/api/product/{id}/")
    fun getProduct(@Header("Authorization") authToken: String?, @Path("id") id: String): Response<ProductResponseData>

    @GET("/api/product/{id}/review/")
    fun getReviews(@Path("id") id: String): Response<ReviewsResponseData>

    @POST("/api/product/{id}/review/add/")
    fun addReview(@Header("Authorization") authToken: String?,@Path("id") id: String, @Body requestData: AddReviewRequestData): Response<AddReviewResponseData>

    @GET("/api/product/")
    fun getListProduct(): Response<GetListProductResponseData>

    @GET("/api/chat/")
    fun getChats(@Header("Authorization") authToken: String?): Response<List<Chat>>

    @GET("/api/shop/{id}/")
    fun getShop(@Header("Authorization") authToken: String?,@Path("id") id: String): Response<GetShopResponseData>

}