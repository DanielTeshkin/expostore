package com.expostore.api

import com.expostore.api.base.BaseListResponse
import com.expostore.api.pojo.addreview.AddReviewRequestData
import com.expostore.api.pojo.addreview.AddReviewResponseData
import com.expostore.api.pojo.addshop.AddShopRequestData
import com.expostore.api.pojo.comparison.ComparisonProductData
import com.expostore.api.pojo.comparison.ComparisonResult
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.api.pojo.createtender.CreateTenderRequestData
import com.expostore.api.pojo.createtender.CreateTenderResponseData
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getchats.*
import com.expostore.api.pojo.getcities.CityResponse
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.api.pojo.getprofile.EditProfileRequest
import com.expostore.api.pojo.getprofile.GetProfileResponseData
import com.expostore.api.pojo.getreviews.ReviewsResponseData
import com.expostore.api.pojo.getshop.GetShopResponseData
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.api.pojo.gettenderlist.TenderPage
import com.expostore.api.pojo.gettenderlist.TenderRequest
import com.expostore.api.pojo.gettenderlist.TenderResponse
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.api.request.*
import com.expostore.api.response.*
import com.expostore.api.response.ProductResponse
import com.expostore.model.category.CharacteristicFilterModel
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.*

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

    @POST("/api/shop/create/")
    suspend fun shopCreate(@Body request:AddShopRequestData):Response<ShopResponse>

    @PUT("/api/shop/")
    suspend fun editShop(@Body request:AddShopRequestData):Response<ShopResponse>

    @GET("/api/shop/")
    suspend fun getMyShop():Response<ShopResponse>



    @Multipart
    @POST("/api/chat/file/create/")
    suspend fun fileCreate(@Part file: MultipartBody.Part, @Part("filename")name: RequestBody):Response<ResponseFile>

    @POST("/api/chat/item/{id}/message/create/")
    suspend fun messageCreate(@Body request: MessageRequest, @Path("id") id: String):Response<MessageRequest>

    @POST("/api/chat/item/{id}/message/create/")
    suspend fun messageFileOrImage(@Body request: FileOrImageMessage, @Path("id") id: String):Response<MessageRequest>

    @GET("/api/cities/")
    suspend fun getCities(): Response<List<CityResponse>>

    @POST("/api/shop/{id}/product/create/")
    suspend fun createProduct(@Path("id") id: String,@Body request:ProductUpdateRequest):Response<CreateResponseProduct>

    @PUT("api/product/{id}/update/")
    suspend fun updateProduct(@Path("id") id: String,@Body request:ProductUpdateRequest):Response<CreateResponseProduct>

    @GET("/api/profile/")
    suspend fun getProfile(): Response<GetProfileResponseData>

    @PUT("/api/profile/")
    suspend fun editProfile(@Body request: EditProfileRequestData): Response<EditProfileResponseData>

    @PATCH("/api/profile/")
    suspend fun patchProfile(@Body request: EditProfileRequest): Response<EditResponseProfile>

    @GET("/api/selection/product/")
    suspend fun getCategories(): Response<List<SelectionResponse>>

    @GET("/api/advertising/selection/")
    suspend fun getCategoryAdvertising(): Response<List<CategoryAdvertisingResponse>>

    @GET("/api/advertising/main/")
    suspend fun getCategoryAdvertisingMain(): Response<List<CategoryAdvertisingResponse>>

    @GET("/api/selection/user/product/")
    suspend fun getUserSelection():Response<List<SelectionResponse>>

    @POST("/api/selection/user/product/create/")
    suspend fun createUserSelection(@Body selectionRequest: SelectionRequest):Response<SelectionResponse>

    @PATCH("/api/selection/user/product/{id}/add_product/")
    suspend fun addProductToUserSelection(@Path("id")id:String, @Body products:ProductsSelection):Response<SelectionResponse>

    @POST("/api/product/{id}/note/create/")
    suspend fun createNote(@Path("id") id: String, @Body request:NoteRequest ) :Response<NoteResponse>

    @POST("/api/product/{id}/note/")
    suspend fun updateNote(@Path("id") id: String,@Body request:NoteRequest) :Response<NoteResponse>

    @GET("/api/search/list/")
    suspend fun getSaveSearchList():Response<List<SaveSearchResponse>>

    @POST("/api/search/save/")
    suspend fun saveSearch(@Body saveSearchRequest: SaveSearchRequest):Response<SaveSearchResponse>




    //@GET("/api/product/elected/list/")
   // suspend fun getSearchList(): Response<List<GetFavoritesListResponseData>>


    @POST("/api/image/save/")
    suspend fun saveImage(@Body request: List<SaveImageRequestData>): Response<SaveImageResponseData>

    @GET("/api/product/category/")
    suspend fun getProductCategory(): Response<List<ProductCategory>>

    @GET("/api/product/category/{id}/characteristic/")
    suspend fun getCategoryCharacteristic(@Path("id") id: String): Response<List<CategoryCharacteristicResponse>>


    @GET("/api/product/{id}/")
    suspend fun getProduct(@Path("id") id: String): Response<ProductResponseData>

    @PATCH("/api/product/{id}/status/draft/")
    suspend fun takeOffProduct(@Path("id") id: String): Response<ProductResponse>

    @PATCH("/api/product/{id}/status/draft/")
    suspend fun saveToDraft(@Path("id") id: String,@Body request: ProductUpdateRequest): Response<ProductResponse>


    @GET("/api/product/{id}/review/")
    suspend fun getReviews(@Path("id") id: String): Response<ReviewsResponseData>

    @POST("/api/product/{id}/review/add/")
    suspend fun addReview(@Path("id") id: String, @Body requestData: AddReviewRequestData): Response<AddReviewResponseData>

    @POST("/api/product/")
    suspend fun getListProduct(
        @Query("page") page: Int?,
        @Body filterRequest: FilterRequest
    ): Response<BaseListResponse<ProductResponse>>

    @GET("/api/product/")
    suspend fun getProducts(
        @Query("page") page: Int?,
        @Body filterRequest: FilterRequest
    ): Response<BaseListResponse<ProductResponse>>

    @PATCH("/api/product/{id}/status/published/")
    suspend fun publishedProduct(
        @Path("id") id:String
    ): Response<ProductResponse>


    @GET("/api/product/my")
    suspend fun getMyListProduct(@Query("status") status: String?): Response<BaseListResponse<ProductResponse>>

    @GET("/api/shop/{id}/")
    suspend fun getShop(@Path("id") id: String): Response<GetShopResponseData>

    @DELETE("/api/selection/user/product/{id}/")
    suspend fun deleteUserSelection(@Path("id") id:String):Response<SelectionResponse>

    @DELETE("/api/search/{id}/")
    suspend fun deleteSaveSearch(@Path("id") id:String) : Response<SaveSearchResponse>

    @GET("/api/reviews/")
    suspend fun getReviews():Response<ReviewsResponse>



    //tender

    @POST("/api/tender/")
    suspend fun getTenders(
        @Query("page") page: Int?,
        @Body filterRequest: FilterRequest

    ): Response<BaseListResponse<Tender>>

    @GET("/api/tender/my")
    suspend fun getMyTenders(@Query("status") status: String?): Response<TenderPage>

    @POST("api/tender/create/")
    suspend fun createTender(@Body tender: TenderRequest): Response<TenderResponse>

    @PATCH("/api/tender/{id}/status/published/")
    suspend fun publishedTender(
        @Path("id") id:String
    ): Response<TenderResponse>

    @PATCH("/api/tender/{id}/status/draft/")
    suspend fun takeOffTender(@Path("id") id: String): Response<TenderResponse>

    @PATCH("api/tender/{id}/update/")
    suspend fun updateTender(@Path("id") id: String,@Body request:TenderRequest):Response<TenderResponse>








    //favorite
    @POST("/api/product/{id}/elected/select/")
    suspend fun selectFavorite(@Path("id") id: String, @Body note: NoteRequest): Response<SelectFavoriteResponseData>

    @POST("/api/product/{id}/elected/update/")
    suspend fun updateFavoriteProduct(@Path("id") id: String, @Body note: NoteRequest): Response<SelectFavoriteResponseData>

    @GET("/api/product/elected/list/")
    suspend fun getFavoritesList(): Response<List<GetFavoritesListResponseData>>

    @POST("/api/tender/{id}/elected/select/")
    suspend fun selectFavoriteTender(@Path("id") id: String,@Body notes: NoteRequest): Response<SelectFavoriteTenderResponseData>

    @POST("/api/tender/{id}/elected/update/")
    suspend fun updateFavoriteTender(@Path("id") id: String, @Body note: NoteRequest): Response<SelectFavoriteTenderResponseData>

    @GET("/api/tender/elected/list/")
    suspend fun getFavoritesTenderList(): Response<List<TenderFavoriteResponse>>

    //chat
    @POST("/api/chat/create/")
    suspend fun chatCreateProduct(@Body chatCreateRequest: ProductChat):Response<ChatResponse>

    @POST("/api/chat/create/")
    suspend fun chatCreateTender(@Body chatCreateRequest: TenderChat):Response<ChatResponse>

    @GET("/api/chat/")
    suspend fun getChats(): Response<List<ChatResponse>>

    @GET("/api/chat/item/{id}/")
    suspend fun getChat(@Path("id") id: String): Response<ItemChatResponse>

    @DELETE("api/chat/{id}")
    suspend fun deleteMainChat(@Path("id") id: String):Response<ChatResponse>

    @DELETE("api/chat/item/{id}")
    suspend fun deleteChat(@Path("id") id: String):Response<ItemChatResponse>


    //comparison
    @POST("/api/product/select_for_comparison/")
    suspend fun addProductToComparison(@Body products:List<ComparisonProductData>):Response<List<ComparisonProductData>>

    @POST("/api/product/comparison/")
    suspend fun comparison(@Body products:List<ComparisonProductData>):Response<ComparisonResult>

    @GET("/api/product/get_select_for_comparison/")
    suspend fun getComparisonProducts(): Response<ProductResponse>

    //personal product
    @POST("/api/product/personal/create/")
    suspend fun createPersonalProduct(@Body request:ProductUpdateRequest):Response<CreateResponseProduct>

    @GET("/api/product/personal/")
    suspend fun getPersonalProducts():Response<BaseListResponse<ProductResponse>>

    @DELETE("/api/product/personal/{id}")
    suspend fun deletePersonalProduct(@Path("id") id: String):Response<ProductResponse>






}