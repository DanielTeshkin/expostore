package com.expostore.api

import com.expostore.api.base.BaseListResponse
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

import com.expostore.api.pojo.getchats.*

import com.expostore.api.pojo.getcities.City
import com.expostore.api.pojo.getcities.CityResponse
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.api.pojo.getprofile.EditProfileRequest
import com.expostore.api.pojo.getprofile.GetProfileResponseData
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
import com.expostore.api.request.RefreshTokenRequest
import com.expostore.api.response.CategoryAdvertisingResponse
import com.expostore.api.response.CategoryCharacteristicResponse
import com.expostore.api.response.CategoryResponse
import com.expostore.api.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface ServerApi {

    @POST("/api/sign-in/")
    suspend fun authorization(@Body request: SignInRequestData): Response<SignInResponseData>

    @POST("/api/token/refresh/")
    suspend fun refresh(@Body refresh: RefreshTokenRequest): Response<SignInResponseData>

    @POST("/api/confirm/create/")
    suspend fun confirmNumber(@Body request: ConfirmNumberRequestData): Response<ConfirmNumberResponseData>

    @POST("/api/confirm/confirmed/")
    suspend fun confirmCode(@Body request: ConfirmCodeRequestData): Response<ConfirmCodeResponseData>

    @POST("/api/sign-up/")
    suspend fun registration(@Body request: SignUpRequestData): Response<SignUpResponseData>

    @POST("/api/chat/create/")
    suspend fun chatCreate(@Body request:ResponseMainChat,@Path("id") id: String):Response<ResponseMainChat>

    @Multipart
    @POST("/api/chat/file/create/")
    suspend fun fileCreate(@Part file: MultipartBody.Part, @Part("filename")name: RequestBody):Response<ResponseFile>

    @POST("/api/chat/item/{id}/message/create/")
    suspend fun messageCreate(@Body request: MessageRequest, @Path("id") id: String):Response<MessageRequest>

    @POST("/api/chat/item/{id}/message/create/")
    suspend fun messageFileOrImage(@Body request: FileOrImageMessage, @Path("id") id: String):Response<MessageRequest>

    @GET("/api/cities/")
    suspend fun getCities(): Response<List<CityResponse>>

    @GET("/api/profile/")
    suspend fun getProfile(): Response<GetProfileResponseData>

    @PUT("/api/profile/")
    suspend fun editProfile(@Body request: EditProfileRequestData): Response<EditProfileResponseData>

    @PATCH("/api/profile/")
    suspend fun patchProfile(@Body request: EditProfileRequest): Response<EditProfileRequest>

    @GET("/api/selection/product/")
    suspend fun getCategories(): Response<List<CategoryResponse>>

    @GET("/api/advertising/selection/")
    suspend fun getCategoryAdvertising(): Response<List<CategoryAdvertisingResponse>>

    @GET("/api/advertising/main/")
    suspend fun getCategoryAdvertisingMain(): Response<List<CategoryAdvertisingResponse>>

    //TODO изменить
    @POST("/api/product/{id}/elected/select/")
    suspend fun selectFavorite(@Path("id") id: String): Response<SelectFavoriteResponseData>

    @GET("/api/product/elected/list/")
    suspend fun getFavoritesList(): Response<List<GetFavoritesListResponseData>>

    @POST("/api/tender/create/")
    suspend fun createTender(@Body requestData: CreateTenderRequestData): Response<CreateTenderResponseData>

    @POST("/api/image/save/")
    suspend fun saveImage(@Body request: SaveImageRequestData): Response< SaveImageResponseData>

    @GET("/api/product/category/")
    suspend fun getProductCategory(): Response<List<ProductCategory>>

    @GET("/api/product/category/{id}")
    suspend fun getCategoryCharacteristic(@Path("id") id: String): Response<List<CategoryCharacteristicResponse>>

    @GET("/api/tender/")
    suspend fun getTenders(): Response<List<Tender>>

    @GET("/api/product/{id}/")
    suspend fun getProduct(@Path("id") id: String): Response<ProductResponseData>

    @GET("/api/product/{id}/review/")
    suspend fun getReviews(@Path("id") id: String): Response<ReviewsResponseData>

    @POST("/api/product/{id}/review/add/")
    suspend fun addReview(@Path("id") id: String, @Body requestData: AddReviewRequestData): Response<AddReviewResponseData>

    @GET("/api/product/")
    suspend fun getListProduct(@Query("page") page: Int?, @Query("q") query: String?): Response<BaseListResponse<ProductResponse>>

    @GET("/api/chat/")
    suspend fun getChats(): Response<List<ResponseMainChat>>

    @GET("/api/chat/item/{id}")
    suspend fun getChat(@Path("id") id: String): Response<ItemChatResponse>

    @GET("/api/shop/{id}/")
    suspend fun getShop(@Path("id") id: String): Response<GetShopResponseData>

    @DELETE("api/chat/{id}")
    suspend fun deleteMainChat(@Path("id") id: String):Response<ResponseMainChat>

    @DELETE("api/chat/item/{id}")
    suspend fun deleteChat(@Path("id") id: String):Response<ItemChatResponse>

}