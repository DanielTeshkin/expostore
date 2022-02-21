package com.expostore.api

import com.expostore.api.pojo.ApiResponse
import com.expostore.api.pojo.BaseApiResponse
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
import retrofit2.Response

/**
 * @author Fedotov Yakov
 */
class ApiWorkerImpl(private val serverApi: ServerApi) : ApiWorker {

    private inline fun <T> processResponse(response: Response<T>): BaseApiResponse<T> =
        ApiResponse.create(response)

    private inline fun <T> processListResponse(response: Response<List<T>>): BaseApiResponse<List<T>> =
        ApiResponse.createList(response)

    override suspend fun authorization(request: SignInRequestData): BaseApiResponse<SignInResponseData> =
        processResponse(serverApi.authorization(request))

    override suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData> =
        processResponse(serverApi.confirmNumber(request))

    override suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData> =
        processResponse(serverApi.confirmCode(request))

    override suspend fun registration(request: SignUpRequestData): BaseApiResponse<SignUpResponseData> =
        processResponse(serverApi.registration(request))

    override suspend fun getCities(authToken: String?): BaseApiResponse<List<City>> =
        processListResponse(serverApi.getCities(authToken))

    override suspend fun editProfile(
        authToken: String?,
        request: EditProfileRequestData
    ): BaseApiResponse<EditProfileResponseData> =
        processResponse(serverApi.editProfile(authToken, request))

    override suspend fun getCategories(authToken: String?): BaseApiResponse<List<Category>> =
        processListResponse(serverApi.getCategories(authToken))

    override suspend fun getCategoryAdvertising(authToken: String?): BaseApiResponse<List<CategoryAdvertising>> =
        processListResponse(serverApi.getCategoryAdvertising(authToken))

    override suspend fun selectFavorite(
        authToken: String?,
        id: String
    ): BaseApiResponse<SelectFavoriteResponseData> =
        processResponse(serverApi.selectFavorite(authToken, id))

    override suspend fun getFavoritesList(authToken: String?): BaseApiResponse<List<GetFavoritesListResponseData>> =
        processListResponse(serverApi.getFavoritesList(authToken))

    override suspend fun createTender(
        authToken: String?,
        requestData: CreateTenderRequestData
    ): BaseApiResponse<CreateTenderResponseData> =
        processResponse(serverApi.createTender(authToken, requestData))

    override suspend fun saveImage(
        authToken: String?,
        request: SaveImageRequestData
    ): BaseApiResponse<SaveImageResponseData> =
        processResponse(serverApi.saveImage(authToken, request))

    override suspend fun getProductCategory(authToken: String?): BaseApiResponse<List<ProductCategory>> =
        processListResponse(serverApi.getProductCategory(authToken))

    override suspend fun getTenders(authToken: String?): BaseApiResponse<List<Tender>> =
        processListResponse(serverApi.getTenders(authToken))

    override suspend fun getProduct(
        authToken: String?,
        id: String
    ): BaseApiResponse<ProductResponseData> =
        processResponse(serverApi.getProduct(authToken, id))

    override suspend fun getReviews(id: String): BaseApiResponse<ReviewsResponseData> =
        processResponse(serverApi.getReviews(id))

    override suspend fun addReview(
        authToken: String?,
        id: String,
        requestData: AddReviewRequestData
    ): BaseApiResponse<AddReviewResponseData> =
        processResponse(serverApi.addReview(authToken, id, requestData))

    override suspend fun getListProduct(): BaseApiResponse<GetListProductResponseData> =
        processResponse(serverApi.getListProduct())

    override suspend fun getChats(authToken: String?): BaseApiResponse<List<Chat>> =
        processListResponse(serverApi.getChats(authToken))

    override suspend fun getShop(
        authToken: String?,
        id: String
    ): BaseApiResponse<GetShopResponseData> =
        processResponse(serverApi.getShop(authToken, id))
}