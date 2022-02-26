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
    suspend fun authorization(@Body request: SignInRequestData): Response<SignInResponseData>

    @POST("/api/confirm/create/")
    suspend fun confirmNumber(@Body request: ConfirmNumberRequestData): Response<ConfirmNumberResponseData>

    @POST("/api/confirm/confirmed/")
    suspend fun confirmCode(@Body request: ConfirmCodeRequestData): Response<ConfirmCodeResponseData>

    @POST("/api/sign-up/")
    suspend fun registration(@Body request: SignUpRequestData): Response<SignUpResponseData>

    @GET("/api/cities/")
    suspend fun getCities(): Response<List<City>>

    @PUT("/api/profile/")
    suspend fun editProfile(@Body request: EditProfileRequestData): Response<EditProfileResponseData>

    @GET("/api/selection/product/")
    suspend fun getCategories(): Response<List<Category>>

    @GET("/api/advertising/selection/")
    suspend fun getCategoryAdvertising(): Response<List<CategoryAdvertising>>

    //TODO изменить
    @POST("/api/product/{id}/elected/select/")
    suspend fun selectFavorite(@Path("id") id: String): Response<SelectFavoriteResponseData>

    @GET("/api/product/elected/list/")
    suspend fun getFavoritesList(): Response<List<GetFavoritesListResponseData>>

    @POST("/api/tender/create/")
    suspend fun createTender(@Body requestData: CreateTenderRequestData): Response<CreateTenderResponseData>

    @POST("/api/image/save/")
    suspend fun saveImage(@Body request: SaveImageRequestData): Response<SaveImageResponseData>

    @GET("/api/product/category/")
    suspend fun getProductCategory(): Response<List<ProductCategory>>

    @GET("/api/tender/")
    suspend fun getTenders(): Response<List<Tender>>

    @GET("/api/product/{id}/")
    suspend fun getProduct(@Path("id") id: String): Response<ProductResponseData>

    @GET("/api/product/{id}/review/")
    suspend fun getReviews(@Path("id") id: String): Response<ReviewsResponseData>

    @POST("/api/product/{id}/review/add/")
    suspend fun addReview(@Path("id") id: String, @Body requestData: AddReviewRequestData): Response<AddReviewResponseData>

    @GET("/api/product/")
    suspend fun getListProduct(): Response<GetListProductResponseData>

    @GET("/api/chat/")
    suspend fun getChats(): Response<List<Chat>>

    @GET("/api/shop/{id}/")
    suspend fun getShop(@Path("id") id: String): Response<GetShopResponseData>

}