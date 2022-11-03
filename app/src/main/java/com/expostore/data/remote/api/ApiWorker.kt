package com.expostore.data.remote.api

import com.expostore.data.remote.api.base.BaseApiResponse
import com.expostore.data.remote.api.base.BaseListResponse
import com.expostore.data.remote.api.pojo.addreview.AddReviewRequestData
import com.expostore.data.remote.api.pojo.addreview.AddReviewResponseData
import com.expostore.data.remote.api.pojo.addshop.AddShopRequestData
import com.expostore.data.remote.api.pojo.comparison.ComparisonProductData
import com.expostore.data.remote.api.pojo.comparison.ComparisonResult
import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.data.remote.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.data.remote.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.data.remote.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.data.remote.api.pojo.editprofile.EditProfileRequestData
import com.expostore.data.remote.api.pojo.editprofile.EditProfileResponseData

import com.expostore.data.remote.api.pojo.getchats.*

import com.expostore.data.remote.api.pojo.getcities.CityResponse
import com.expostore.data.remote.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.data.remote.api.pojo.getproduct.ProductResponseData
import com.expostore.data.remote.api.pojo.getprofile.EditProfileRequest
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.data.remote.api.pojo.getreviews.ReviewsResponseData
import com.expostore.data.remote.api.pojo.getshop.GetShopResponseData
import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.expostore.data.remote.api.pojo.gettenderlist.TenderPage
import com.expostore.data.remote.api.pojo.gettenderlist.TenderRequest
import com.expostore.data.remote.api.pojo.gettenderlist.TenderResponse
import com.expostore.data.remote.api.pojo.productcategory.ProductCategory
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveFileResponseData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.remote.api.pojo.selectfavorite.SelectFavoriteTenderResponseData
import com.expostore.data.remote.api.pojo.signin.SignInResponseData
import com.expostore.data.remote.api.pojo.signup.ResetPasswordRequest
import com.expostore.data.remote.api.pojo.signup.SignUpRequestData
import com.expostore.data.remote.api.pojo.signup.SignUpResponseData
import com.expostore.data.remote.api.request.*
import com.expostore.data.remote.api.response.*
import com.expostore.data.remote.api.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiWorker {
    suspend fun authorization(
        username: String,
        password: String
    ): BaseApiResponse<SignInResponseData>

    suspend fun refresh(refreshToken: String): BaseApiResponse<SignInResponseData>

    suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData>

    suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData>

    suspend fun registration(request: SignUpRequestData): BaseApiResponse<SignUpResponseData>

    suspend fun confirmPassNumber( request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData>

    suspend fun confirmPassCode( request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData>

    suspend fun resetPassword( request: ResetPasswordRequest): BaseApiResponse<ResetPasswordRequest>

    suspend fun shopCreate(request: AddShopRequestData):BaseApiResponse<ShopResponse>

    suspend fun editShop(request: AddShopRequestData):BaseApiResponse<ShopResponse>

    suspend fun getMyShop():BaseApiResponse<ShopResponse>


    suspend fun getPriceHistory(id:String):BaseApiResponse<List<PriceHistoryResponse>>

    suspend fun fileCreate(file: MultipartBody.Part,name:RequestBody):BaseApiResponse<ResponseFile>
   suspend fun saveFileBase64( request: List<SaveFileRequestData>):BaseApiResponse<SaveFileResponseData>

    suspend fun messageCreate(request:  MessageRequest, id: String):BaseApiResponse<MessageRequest>

    suspend fun messageFileOrImage(request: FileOrImageMessage, id: String):BaseApiResponse<MessageRequest>

    suspend fun getCities(): BaseApiResponse<List<CityResponse>>

    suspend fun getProfile(): BaseApiResponse<GetProfileResponseData>

    suspend fun createProduct(id: String,request: ProductUpdateRequest):BaseApiResponse<CreateResponseProduct>

    suspend fun updateProduct(id: String, request: ProductUpdateRequest):BaseApiResponse<CreateResponseProduct>

    suspend fun getReviews():BaseApiResponse<ReviewsResponse>

    suspend fun saveToDraft( id: String,request: ProductUpdateRequest): BaseApiResponse<ProductResponse>

    suspend fun editProfile(request: EditProfileRequestData): BaseApiResponse<EditProfileResponseData>

    suspend fun patchProfile( request: EditProfileRequest): BaseApiResponse<EditResponseProfile>

    suspend fun getCategories(): BaseApiResponse<List<SelectionResponse>>

    suspend fun getCategoryAdvertising(): BaseApiResponse<List<CategoryAdvertisingResponse>>

    suspend fun getCategoryAdvertisingMain(): BaseApiResponse<List<CategoryAdvertisingResponse>>

    suspend fun getUserSelection():BaseApiResponse<List<SelectionResponse>>

    suspend fun createUserSelection(selectionRequest: SelectionRequest):BaseApiResponse<SelectionResponse>

    suspend fun addProductToUserSelection(id:String, products:ProductsSelection):BaseApiResponse<SelectionResponse>

    suspend fun getSaveSearchList():BaseApiResponse<List<SaveSearchResponse>>

    suspend fun saveSearch( saveSearchRequest: SaveSearchRequest):BaseApiResponse<SaveSearchResponse>
    //TODO изменить
    suspend fun selectFavorite(id: String, notes: NoteRequest): BaseApiResponse<SelectFavoriteResponseData>

    suspend fun updateFavoriteProduct(id: String, notes: NoteRequest): BaseApiResponse<SelectFavoriteResponseData>

    suspend fun getFavoritesList(): BaseApiResponse<List<GetFavoritesListResponseData>>

    suspend fun selectFavoriteTender( id: String, notes: NoteRequest): BaseApiResponse<SelectFavoriteTenderResponseData>

    suspend fun updateFavoriteTender( id: String,  note: NoteRequest): BaseApiResponse<SelectFavoriteTenderResponseData>

    suspend fun getFavoritesTenderList(): BaseApiResponse<List<TenderFavoriteResponse>>

    suspend fun createTender(requestData: TenderRequest): BaseApiResponse<TenderResponse>

    suspend fun saveImage(request: List<SaveImageRequestData>): BaseApiResponse<SaveImageResponseData>


    suspend fun createNote(id: String, request: NoteRequest) :BaseApiResponse<NoteResponse>


    suspend fun updateNote( id: String, request: NoteRequest) :BaseApiResponse<NoteResponse>

    suspend fun getProductCategory(): BaseApiResponse<List<ProductCategory>>

    suspend fun getCategoryCharacteristic(id: String): BaseApiResponse<List<CategoryCharacteristicResponse>>

    suspend fun getTenders(  page: Int?=null,
                             filterRequest: FilterRequest): BaseApiResponse<BaseListResponse<Tender>>

    suspend fun getMyTenders(status: String): BaseApiResponse<TenderPage>

    suspend fun getProduct(id: String): BaseApiResponse<ProductResponse>

    suspend fun publishedProduct( id:String): BaseApiResponse<ProductResponse>

    suspend fun takeOffProduct(id: String): BaseApiResponse<ProductResponse>

    suspend fun getReviews(id: String): BaseApiResponse<ReviewsResponseData>

    suspend fun addReview(
        id: String,
        requestData: AddReviewRequestData
    ): BaseApiResponse<AddReviewResponseData>

    suspend fun getListProduct(

        page: Int?=null,
        filterRequest: FilterRequest

    ): BaseApiResponse<BaseListResponse<ProductResponse>>
    suspend fun getProducts(
        page: Int?=null,
     filterRequest: FilterRequest?=null
    ): BaseApiResponse<BaseListResponse<ProductResponse>>

    suspend fun getMyListProduct(
        status:String?=null
    ): BaseApiResponse<BaseListResponse<ProductResponse>>

    suspend fun getChats(): BaseApiResponse<List<ChatResponse>>
    suspend fun createChat(id:String,flag:String) :BaseApiResponse<ChatResponse>
    suspend fun getChat(id:String): BaseApiResponse<ItemChatResponse>
    suspend fun getShop(id: String): BaseApiResponse<GetShopResponseData>
    suspend fun getMainChat(id:String):BaseApiResponse<ChatResponse>
    suspend fun deleteMainChat(id: String):BaseApiResponse<ChatResponse>
    suspend fun deleteChat( id: String):BaseApiResponse<ItemChatResponse>
    suspend fun deleteUserSelection( id:String):BaseApiResponse<SelectionResponse>
    suspend fun deleteSaveSearch( id:String) : BaseApiResponse<SaveSearchResponse>


    suspend fun publishedTender(
        id:String
    ): BaseApiResponse<TenderResponse>

    suspend fun updateUserSelection( id:String,  selectionRequest: SelectionRequest): BaseApiResponse<SelectionResponse>
    suspend fun takeOffTender( id: String): BaseApiResponse<TenderResponse>
    suspend fun getTender(id:String): BaseApiResponse<Tender>
    suspend fun updateTender( id: String, request:TenderRequest):BaseApiResponse<TenderResponse>

    //comparison

    suspend fun addProductToComparison( products:List<ComparisonProductData>):BaseApiResponse<List<ComparisonProductData>>
    suspend fun comparison( products:List<ComparisonProductData>):BaseApiResponse<ComparisonResult>
    suspend fun getComparisonProducts(): BaseApiResponse<List<ProductResponse>>
    suspend fun deleteFromComparisonProducts(id: String)
    :BaseApiResponse<List<ComparisonProductData>>

    //personal product

    suspend fun createPersonalProduct(request:ProductUpdateRequest):BaseApiResponse<CreateResponseProduct>
    suspend fun getPersonalProducts():BaseApiResponse<BaseListResponse<ProductResponse>>
    suspend fun deletePersonalProduct(id: String):BaseApiResponse<*>
    suspend fun getPersonalProduct(id:String):BaseApiResponse<ProductResponse>


}