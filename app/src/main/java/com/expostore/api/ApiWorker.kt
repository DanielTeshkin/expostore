package com.expostore.api

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
import retrofit2.http.*

/**
 * @author Fedotov Yakov
 */
interface ApiWorker {
    suspend fun authorization(username: String, password: String): BaseApiResponse<SignInResponseData>

    suspend fun confirmNumber(request: ConfirmNumberRequestData): BaseApiResponse<ConfirmNumberResponseData>

    suspend fun confirmCode(request: ConfirmCodeRequestData): BaseApiResponse<ConfirmCodeResponseData>

    suspend fun registration(request: SignUpRequestData): BaseApiResponse<SignUpResponseData>

    suspend fun getCities(): BaseApiResponse<List<City>>

    suspend fun editProfile(@Body request: EditProfileRequestData): BaseApiResponse<EditProfileResponseData>

    suspend fun getCategories(): BaseApiResponse<List<Category>>

    suspend fun getCategoryAdvertising(): BaseApiResponse<List<CategoryAdvertising>>

    //TODO изменить
    suspend fun selectFavorite(id: String): BaseApiResponse<SelectFavoriteResponseData>

    suspend fun getFavoritesList(): BaseApiResponse<List<GetFavoritesListResponseData>>

    suspend fun createTender(requestData: CreateTenderRequestData): BaseApiResponse<CreateTenderResponseData>

    suspend fun saveImage(request: SaveImageRequestData): BaseApiResponse<SaveImageResponseData>

    suspend fun getProductCategory(): BaseApiResponse<List<ProductCategory>>

    suspend fun getTenders(): BaseApiResponse<List<Tender>>

    suspend fun getProduct(id: String): BaseApiResponse<ProductResponseData>

    suspend fun getReviews(id: String): BaseApiResponse<ReviewsResponseData>

    suspend fun addReview(id: String, requestData: AddReviewRequestData): BaseApiResponse<AddReviewResponseData>

    suspend fun getListProduct(): BaseApiResponse<GetListProductResponseData>

    suspend fun getChats(): BaseApiResponse<List<Chat>>

    suspend fun getShop(id: String): BaseApiResponse<GetShopResponseData>

}