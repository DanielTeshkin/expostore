package com.expostore.api

import android.content.Context
import com.expostore.R
import com.expostore.api.base.ApiException
import com.expostore.api.base.ApiResponse
import com.expostore.api.base.BaseApiResponse
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
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException


class ApiWorkerImpl(private val serverApi: ServerApi, private val context: Context) : ApiWorker {

    private suspend inline fun <T> processResponse(crossinline action: suspend () -> Response<T>): BaseApiResponse<T> {
        try {
            return ApiResponse.create(action.invoke())
        } catch (throwable: Throwable) {
            throw processError(throwable)
        }
    }

    private suspend inline fun <T> processListResponse(crossinline action: suspend () -> Response<List<T>>): BaseApiResponse<List<T>> {
        try {
            return ApiResponse.createList(action.invoke())
        } catch (throwable: Throwable) {
            throw processError(throwable)
        }
    }

    private fun processError(throwable: Throwable) =
        when (throwable) {
            is UnknownServiceException, is UnknownHostException, is ConnectException ->
                ApiException(context.resources.getString(R.string.api_exception_connection))
            is SocketTimeoutException -> ApiException(context.resources.getString(R.string.api_exception_timeout))
            is JsonParseException -> ApiException(context.resources.getString(R.string.api_exception_unknown))
            is JsonSyntaxException -> ApiException(context.resources.getString(R.string.api_exception_unknown))
            else -> throwable
        }

    override suspend fun authorization(
        username: String,
        password: String
    ): BaseApiResponse<SignInResponseData> =
        processResponse { serverApi.authorization(SignInRequestData(username, password)) }

    override suspend fun refresh(refreshToken: String): BaseApiResponse<SignInResponseData> =
        processResponse { serverApi.refresh(RefreshTokenRequest(refreshToken)) }

    override suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData> =
        processResponse { serverApi.confirmNumber(request) }

    override suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData> =
        processResponse { serverApi.confirmCode(request) }

    override suspend fun registration(request: SignUpRequestData): BaseApiResponse<SignUpResponseData> =
        processResponse { serverApi.registration(request) }

    override suspend fun shopCreate(request: AddShopRequestData): BaseApiResponse<ShopResponse> =processResponse {
        serverApi.shopCreate(request) }

    override suspend fun editShop(request: AddShopRequestData): BaseApiResponse<ShopResponse> =processResponse {
        serverApi.editShop(request) }

    override suspend fun getMyShop(): BaseApiResponse<ShopResponse> =processResponse { serverApi.getMyShop() }

    override suspend fun fileCreate(
        file: MultipartBody.Part,
        name:RequestBody
    ): BaseApiResponse<ResponseFile> = processResponse { serverApi.fileCreate(file,name) }

    override suspend fun messageCreate(
        request: MessageRequest,
        id: String
    ): BaseApiResponse< MessageRequest> = processResponse { serverApi.messageCreate(request,id) }

    override suspend fun messageFileOrImage(
        request: FileOrImageMessage,
        id: String
    ): BaseApiResponse<MessageRequest> =processResponse { serverApi.messageFileOrImage(request, id) }

    override suspend fun getProfile(): BaseApiResponse<GetProfileResponseData> =
        processResponse { serverApi.getProfile() }

    override suspend fun createProduct(
        id: String,
        request: ProductUpdateRequest
    ): BaseApiResponse<CreateResponseProduct> =processResponse { serverApi.createProduct(id,request) }

    override suspend fun updateProduct(
        id: String,
        request: ProductUpdateRequest
    ): BaseApiResponse<CreateResponseProduct> = processResponse { serverApi.updateProduct(id,request) }


    override suspend fun getReviews(): BaseApiResponse<ReviewsResponse> = processResponse {
        serverApi.getReviews()
    }

    override suspend fun getCities(): BaseApiResponse<List<CityResponse>> =
        processListResponse { serverApi.getCities() }

    override suspend fun editProfile(
        request: EditProfileRequestData
    ): BaseApiResponse<EditProfileResponseData> =
        processResponse { serverApi.editProfile(request) }

    override suspend fun patchProfile(request: EditProfileRequest): BaseApiResponse<EditResponseProfile> =
        processResponse { serverApi.patchProfile(request) }


    override suspend fun getCategories(): BaseApiResponse<List<SelectionResponse>> =
        processListResponse { serverApi.getCategories() }

    override suspend fun getCategoryAdvertising(): BaseApiResponse<List<CategoryAdvertisingResponse>> =
        processListResponse { serverApi.getCategoryAdvertising() }

    override suspend fun getCategoryAdvertisingMain(): BaseApiResponse<List<CategoryAdvertisingResponse>> =processResponse { serverApi.getCategoryAdvertisingMain() }

    override suspend fun getUserSelection(): BaseApiResponse<List<SelectionResponse>> =processListResponse { serverApi.getUserSelection() }

    override suspend fun createUserSelection(selectionRequest: SelectionRequest): BaseApiResponse<SelectionResponse> =
        processResponse { serverApi.createUserSelection(selectionRequest) }

    override suspend fun addProductToUserSelection(
        id: String,
        products: ProductsSelection
    ): BaseApiResponse<SelectionResponse> = processResponse { serverApi.addProductToUserSelection(id, products) }



    override suspend fun getSaveSearchList(): BaseApiResponse<List<SaveSearchResponse>> =processListResponse { serverApi.getSaveSearchList() }
    override suspend fun saveSearch(saveSearchRequest: SaveSearchRequest): BaseApiResponse<SaveSearchResponse> = processResponse{
        serverApi.saveSearch(saveSearchRequest)
    }

    override suspend fun selectFavorite(
        id: String,
        note:NoteRequest
    ): BaseApiResponse<SelectFavoriteResponseData> =
        processResponse { serverApi.selectFavorite(id,note) }

    override suspend fun updateFavoriteProduct(
        id: String,
        notes: NoteRequest
    ): BaseApiResponse<SelectFavoriteResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoritesList(): BaseApiResponse<List<GetFavoritesListResponseData>> =
        processListResponse { serverApi.getFavoritesList() }

    override suspend fun selectFavoriteTender(
        id: String,
        note: NoteRequest
    ): BaseApiResponse<SelectFavoriteTenderResponseData> =processResponse { serverApi.selectFavoriteTender(id, note) }

    override suspend fun updateFavoriteTender(
        id: String,
        note: NoteRequest
    ): BaseApiResponse<SelectFavoriteTenderResponseData> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoritesTenderList(): BaseApiResponse<List<TenderFavoriteResponse>>
    = processListResponse { serverApi.getFavoritesTenderList() }

    override suspend fun createTender(
        requestData: TenderRequest
    ): BaseApiResponse<TenderResponse> =
        processResponse { serverApi.createTender(requestData) }

    override suspend fun saveImage(
        request: List<SaveImageRequestData>
    ): BaseApiResponse<SaveImageResponseData> =
        processResponse { serverApi.saveImage(request) }

    override suspend fun createNote(id: String, request: NoteRequest): BaseApiResponse<NoteResponse> =
        processResponse { serverApi.createNote(id,request) }
    override suspend fun updateNote(
        id: String,
        request: NoteRequest
    ): BaseApiResponse<NoteResponse> = processResponse { serverApi.updateNote(id,request) }

    override suspend fun getProductCategory(): BaseApiResponse<List<ProductCategory>> =
        processListResponse { serverApi.getProductCategory() }

    override suspend fun getCategoryCharacteristic(id: String): BaseApiResponse<List<CategoryCharacteristicResponse>> =
        processListResponse { serverApi.getCategoryCharacteristic(id) }

    override suspend fun getTenders(page: Int?,filterRequest: FilterRequest): BaseApiResponse<BaseListResponse<Tender>> =
        processResponse { serverApi.getTenders(page,filterRequest) }

    override suspend fun getMyTenders(status: String): BaseApiResponse<TenderPage> =
        processResponse { serverApi.getMyTenders(status) }

    override suspend fun getProduct(
        id: String
    ): BaseApiResponse<ProductResponseData> =
        processResponse { serverApi.getProduct(id) }

    override suspend fun publishedProduct(id: String): BaseApiResponse<ProductResponse> =processResponse {
          serverApi.publishedProduct(id) }


    override suspend fun takeOffProduct(id: String): BaseApiResponse<ProductResponse> =
        processResponse { serverApi.takeOffProduct(id) }



    override suspend fun getReviews(id: String): BaseApiResponse<ReviewsResponseData> =
        processResponse { serverApi.getReviews(id) }

    override suspend fun saveToDraft(
        id: String,
        request: ProductUpdateRequest
    ): BaseApiResponse<ProductResponse> =processResponse { serverApi.saveToDraft(id,request) }

    override suspend fun addReview(
        id: String,
        requestData: AddReviewRequestData
    ): BaseApiResponse<AddReviewResponseData> =
        processResponse { serverApi.addReview(id, requestData) }

    override suspend fun getListProduct(
        page: Int?,
        filterRequest: FilterRequest
    ): BaseApiResponse<BaseListResponse<ProductResponse>> =
        processResponse { serverApi.getListProduct(page,filterRequest) }

    override suspend fun getProducts(
        page: Int?,
        filterRequest: FilterRequest?
    ): BaseApiResponse<BaseListResponse<ProductResponse>> = processResponse { serverApi.getProducts(page,filterRequest?: FilterRequest()) }

    override suspend fun getMyListProduct(status:String?): BaseApiResponse<BaseListResponse<ProductResponse>> =
        processResponse{serverApi.getMyListProduct(status)}


    override suspend fun getChats(): BaseApiResponse<List<ChatResponse>> =
        processListResponse { serverApi.getChats() }

    override suspend fun createChat(id:String,flag:String): BaseApiResponse<ChatResponse> {
       return if(flag=="tender") processResponse { serverApi.chatCreateTender(TenderChat(id)) }
         else processResponse {serverApi.chatCreateProduct(ProductChat(id))}

    }

    override suspend fun getChat(id: String): BaseApiResponse<ItemChatResponse> = processResponse { serverApi.getChat(id) }

    override suspend fun getShop(
        id: String
    ): BaseApiResponse<GetShopResponseData> =
        processResponse { serverApi.getShop(id) }

    override suspend fun deleteMainChat(id: String): BaseApiResponse<ChatResponse> = processResponse { serverApi.deleteMainChat(id) }

    override suspend fun deleteChat(id: String): BaseApiResponse<ItemChatResponse> = processResponse { serverApi.deleteChat(id) }

    override suspend fun deleteUserSelection(id: String): BaseApiResponse<SelectionResponse> =processResponse {serverApi.deleteUserSelection(id) }

    override suspend fun deleteSaveSearch(id: String): BaseApiResponse<SaveSearchResponse> =processResponse { serverApi.deleteSaveSearch(id) }


    override suspend fun publishedTender(id: String): BaseApiResponse<TenderResponse> = processResponse {
        serverApi.publishedTender(id)
    }

    override suspend fun takeOffTender(id: String): BaseApiResponse<TenderResponse> = processResponse {
        serverApi.takeOffTender(id)
    }

    override suspend fun updateTender(
        id: String,
        request: TenderRequest
    ): BaseApiResponse<TenderResponse> = processResponse {
        serverApi.updateTender(id,request)
    }

    override suspend fun addProductToComparison(products: List<ComparisonProductData>):
            BaseApiResponse<List<ComparisonProductData>> = processListResponse { serverApi.addProductToComparison(products) }



    override suspend fun comparison(products: List<ComparisonProductData>): BaseApiResponse<ComparisonResult> = processResponse {
        serverApi.comparison(products)
    }

    override suspend fun getComparisonProducts(): BaseApiResponse<ProductResponse> =processResponse { serverApi.getComparisonProducts() }

    override suspend fun createPersonalProduct(request: ProductUpdateRequest): BaseApiResponse<CreateResponseProduct> =
        processResponse { serverApi.createPersonalProduct(request) }
    override suspend fun getPersonalProducts(): BaseApiResponse<BaseListResponse<ProductResponse>> =
        processResponse { serverApi.getPersonalProducts() }

    override suspend fun deletePersonalProduct(id: String): BaseApiResponse<ProductResponse> =
        processResponse { serverApi.deletePersonalProduct(id) }


}