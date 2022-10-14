package com.expostore.data.local.db
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.expostore.data.local.db.enities.*
import com.expostore.data.local.db.enities.chat.ChatDao
import com.expostore.data.local.db.enities.favorites.FavoriteProductDao
import com.expostore.data.local.db.enities.favorites.FavoriteTenderDao
import com.expostore.data.local.db.enities.selection.SelectionDao
import com.expostore.data.local.db.model.TokenModel
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender

interface LocalWorker {

    suspend fun getChats():List<ChatDao>
    suspend fun saveChats(chats:List<MainChat>)
    suspend fun removeChats()
    suspend fun getProfile(): ProfileDao
    suspend fun saveProfile(model: ProfileDao)
    fun deleteProfile()
    suspend fun getSelection():List<SelectionDao>
    suspend fun getSelectionById(id:String):SelectionDao
    suspend fun getPersonalSelectionById(id: String):SelectionDao
    suspend fun saveSelections(selections: List<SelectionModel>)
    suspend fun removeSelections()
    suspend fun getAdvertising():List<AdvertisingDao>
    suspend fun saveAdvertising(advertising: List<CategoryAdvertisingModel>)
    suspend fun removeAdvertising()
    suspend fun getFavoritesProduct():List<FavoriteProductDao>
    suspend fun saveFavorites(list: List<FavoriteProduct>)
    suspend fun removeFavorites()
    suspend fun getCategories(): List<CategoryDao>
    suspend fun saveCategories(categories:List<ProductCategoryModel>)
    suspend fun removeCategories()
    fun getToken():String?
    fun getRefreshToken():String?
    fun saveToken(tokenModel: TokenModel)
    fun removeToken()
    suspend fun getMyProducts(string: String): MyProductsDao
 suspend fun saveMyProducts(productsDao: MyProductsDao)
 suspend fun removeMyProducts()
 suspend fun getMyTenders(string: String): MyTendersDao
 suspend fun saveMyTenders(tendersDao: MyTendersDao)
 suspend fun removeMyTenders()
 suspend fun getFavoritesTender():List<FavoriteTenderDao>
 suspend fun saveFavoritesTender(list: List<FavoriteTender>)
 suspend fun removeFavoritesTender()
 suspend fun getPersonalSelection():List<SelectionModel>
 suspend fun savePersonalSelections(items:List<SelectionModel>)
 suspend fun removePersonalSelections()
}