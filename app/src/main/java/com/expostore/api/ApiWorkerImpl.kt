package com.expostore.api

import android.content.Context
import com.expostore.R
import com.expostore.api.base.ApiException
import com.expostore.api.base.ApiResponse
import com.expostore.api.base.BaseApiResponse
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
import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.MessageRequest

import com.expostore.api.pojo.getcities.City
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.pojo.getproduct.ProductResponseData
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
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * @author Fedotov Yakov
 */
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

    override suspend fun messageCreate(
        request: MessageRequest,
        id: String
    ): BaseApiResponse< MessageRequest> = processResponse { serverApi.messageCreate(request,id) }

    override suspend fun getProfile(): BaseApiResponse<GetProfileResponseData> =
        processResponse { serverApi.getProfile() }

    override suspend fun getCities(): BaseApiResponse<List<City>> =
        processListResponse { serverApi.getCities() }

    override suspend fun editProfile(
        request: EditProfileRequestData
    ): BaseApiResponse<EditProfileResponseData> =
        processResponse { serverApi.editProfile(request) }

    override suspend fun getCategories(): BaseApiResponse<List<CategoryResponse>> =
        processListResponse { serverApi.getCategories() }

    override suspend fun getCategoryAdvertising(): BaseApiResponse<List<CategoryAdvertisingResponse>> =
        processListResponse { serverApi.getCategoryAdvertising() }

    override suspend fun selectFavorite(
        id: String
    ): BaseApiResponse<SelectFavoriteResponseData> =
        processResponse { serverApi.selectFavorite(id) }

    override suspend fun getFavoritesList(): BaseApiResponse<List<GetFavoritesListResponseData>> =
        processListResponse { serverApi.getFavoritesList() }

    override suspend fun createTender(
        requestData: CreateTenderRequestData
    ): BaseApiResponse<CreateTenderResponseData> =
        processResponse { serverApi.createTender(requestData) }

    override suspend fun saveImage(
        request: SaveImageRequestData
    ): BaseApiResponse<SaveImageResponseData> =
        processResponse { serverApi.saveImage(request) }

    override suspend fun getProductCategory(): BaseApiResponse<List<ProductCategory>> =
        processListResponse { serverApi.getProductCategory() }

    override suspend fun getCategoryCharacteristic(id: String): BaseApiResponse<List<CategoryCharacteristicResponse>> =
        processListResponse { serverApi.getCategoryCharacteristic(id) }

    override suspend fun getTenders(): BaseApiResponse<List<Tender>> =
        processListResponse { serverApi.getTenders() }

    override suspend fun getProduct(
        id: String
    ): BaseApiResponse<ProductResponseData> =
        processResponse { serverApi.getProduct(id) }

    override suspend fun getReviews(id: String): BaseApiResponse<ReviewsResponseData> =
        processResponse { serverApi.getReviews(id) }

    override suspend fun addReview(
        id: String,
        requestData: AddReviewRequestData
    ): BaseApiResponse<AddReviewResponseData> =
        processResponse { serverApi.addReview(id, requestData) }

    override suspend fun getListProduct(
        page: Int?,
        query: String?
    ): BaseApiResponse<BaseListResponse<ProductResponse>> =
        processResponse { serverApi.getListProduct(page, query) }

    override suspend fun getChats(): BaseApiResponse<List<ResponseMainChat>> =
        processListResponse { serverApi.getChats() }

    override suspend fun getChat(id: String): BaseApiResponse<ItemChatResponse> = processResponse { serverApi.getChat(id) }

    override suspend fun getShop(
        id: String
    ): BaseApiResponse<GetShopResponseData> =
        processResponse { serverApi.getShop(id) }

    override suspend fun deleteMainChat(id: String): BaseApiResponse<ResponseMainChat> = processResponse { serverApi.deleteMainChat(id) }

    override suspend fun deleteChat(id: String): BaseApiResponse<ItemChatResponse> = processResponse { serverApi.deleteChat(id) }



}