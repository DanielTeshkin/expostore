package com.expostore.api

import com.expostore.api.base.BaseApiResponse
import com.expostore.api.base.BaseListResponse
import com.expostore.api.pojo.addreview.AddReviewRequestData
import com.expostore.api.pojo.addreview.AddReviewResponseData
import com.expostore.api.pojo.addshop.AddShopRequestData
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
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.api.request.*
import com.expostore.api.response.*
import com.expostore.api.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ApiWorker {
    suspend fun authorization(
        username: String,
        password: String
    ): BaseApiResponse<SignInResponseData>

    suspend fun refresh(refreshToken: String): BaseApiResponse<SignInResponseData>

    suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData>

    suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData>

    suspend fun registration(request: SignUpRequestData): BaseApiResponse<SignUpResponseData>

    suspend fun shopCreate(request: AddShopRequestData):BaseApiResponse<ShopResponse>

    suspend fun editShop(request: AddShopRequestData):BaseApiResponse<ShopResponse>

    suspend fun getMyShop():BaseApiResponse<ShopResponse>

    suspend fun fileCreate(file: MultipartBody.Part,name:RequestBody):BaseApiResponse<ResponseFile>

    suspend fun messageCreate(request:  MessageRequest, id: String):BaseApiResponse<MessageRequest>

    suspend fun messageFileOrImage(request: FileOrImageMessage, id: String):BaseApiResponse<MessageRequest>

    suspend fun getCities(): BaseApiResponse<List<CityResponse>>

    suspend fun getProfile(): BaseApiResponse<GetProfileResponseData>

    suspend fun createProduct(id: String,request: ProductUpdateRequest):BaseApiResponse<ProductResponseUpdate>

    suspend fun updateProduct(id: String, request: ProductUpdateRequest):BaseApiResponse<ProductResponseUpdate>

    suspend fun getReviews():BaseApiResponse<ReviewsResponse>

    suspend fun saveToDraft( id: String,request: ProductUpdateRequest): BaseApiResponse<ProductResponseUpdate>

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

    suspend fun getMyTenders(): BaseApiResponse<TenderPage>

    suspend fun getProduct(id: String): BaseApiResponse<ProductResponseData>

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

    suspend fun deleteMainChat(id: String):BaseApiResponse<ChatResponse>

    suspend fun deleteChat( id: String):BaseApiResponse<ItemChatResponse>

    suspend fun deleteUserSelection( id:String):BaseApiResponse<SelectionResponse>

    suspend fun deleteSaveSearch( id:String) : BaseApiResponse<SaveSearchResponse>


}