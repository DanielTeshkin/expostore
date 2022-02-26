package com.expostore.api

import com.expostore.api.base.ApiResponse
import com.expostore.api.base.BaseApiResponse
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

    override suspend fun authorization(
        username: String,
        password: String
    ): BaseApiResponse<SignInResponseData> =
        processResponse(serverApi.authorization(SignInRequestData(username, password)))

    override suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData> =
        processResponse(serverApi.confirmNumber(request))

    override suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData> =
        processResponse(serverApi.confirmCode(request))

    override suspend fun registration(request: SignUpRequestData): BaseApiResponse<SignUpResponseData> =
        processResponse(serverApi.registration(request))

    override suspend fun getCities(): BaseApiResponse<List<City>> =
        processListResponse(serverApi.getCities())

    override suspend fun editProfile(
        request: EditProfileRequestData
    ): BaseApiResponse<EditProfileResponseData> =
        processResponse(serverApi.editProfile(request))

    override suspend fun getCategories(): BaseApiResponse<List<Category>> =
        processListResponse(serverApi.getCategories())

    override suspend fun getCategoryAdvertising(): BaseApiResponse<List<CategoryAdvertising>> =
        processListResponse(serverApi.getCategoryAdvertising())

    override suspend fun selectFavorite(
        id: String
    ): BaseApiResponse<SelectFavoriteResponseData> =
        processResponse(serverApi.selectFavorite(id))

    override suspend fun getFavoritesList(): BaseApiResponse<List<GetFavoritesListResponseData>> =
        processListResponse(serverApi.getFavoritesList())

    override suspend fun createTender(
        requestData: CreateTenderRequestData
    ): BaseApiResponse<CreateTenderResponseData> =
        processResponse(serverApi.createTender(requestData))

    override suspend fun saveImage(
        request: SaveImageRequestData
    ): BaseApiResponse<SaveImageResponseData> =
        processResponse(serverApi.saveImage(request))

    override suspend fun getProductCategory(): BaseApiResponse<List<ProductCategory>> =
        processListResponse(serverApi.getProductCategory())

    override suspend fun getTenders(): BaseApiResponse<List<Tender>> =
        processListResponse(serverApi.getTenders())

    override suspend fun getProduct(
        id: String
    ): BaseApiResponse<ProductResponseData> =
        processResponse(serverApi.getProduct(id))

    override suspend fun getReviews(id: String): BaseApiResponse<ReviewsResponseData> =
        processResponse(serverApi.getReviews(id))

    override suspend fun addReview(
        id: String,
        requestData: AddReviewRequestData
    ): BaseApiResponse<AddReviewResponseData> =
        processResponse(serverApi.addReview(id, requestData))

    override suspend fun getListProduct(): BaseApiResponse<GetListProductResponseData> =
        processResponse(serverApi.getListProduct())

    override suspend fun getChats(): BaseApiResponse<List<Chat>> =
        processListResponse(serverApi.getChats())

    override suspend fun getShop(
        id: String
    ): BaseApiResponse<GetShopResponseData> =
        processResponse(serverApi.getShop(id))
}