package com.expostore.data.local.db
import android.content.Context
import com.expostore.data.AppPreferences
import com.expostore.data.local.db.enities.*
import com.expostore.data.local.db.enities.chat.ChatDao
import com.expostore.data.local.db.enities.chat.toDao
import com.expostore.data.local.db.enities.favorites.FavoriteProductDao
import com.expostore.data.local.db.enities.favorites.FavoriteTenderDao
import com.expostore.data.local.db.enities.favorites.toDao
import com.expostore.data.local.db.enities.selection.SelectionDao
import com.expostore.data.local.db.enities.selection.toDao
import com.expostore.data.local.db.model.TokenModel
import com.expostore.data.local.db.model.toDao
import com.expostore.data.local.db.model.toModel
import com.expostore.data.remote.api.pojo.getprofile.GetProfileResponseData
import com.expostore.model.category.CategoryAdvertisingModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender

 class LocalWorkerImpl(private val localDataApi: LocalDataApi, private val context: Context):LocalWorker {
    override  fun getToken(): String? = AppPreferences.getSharedPreferences(context).getString("token", "")
    override fun getRefreshToken(): String? =AppPreferences.getSharedPreferences(context).getString(
        "refresh",
        ""
    )

    override fun saveToken(tokenModel: TokenModel) = AppPreferences
        .getSharedPreferences(context).edit().putString("token", tokenModel.access)
        .putString("refresh", tokenModel.refresh)
        .apply()

    override  fun removeToken() = AppPreferences
        .getSharedPreferences(context).edit().putString("token", "")
        .putString("refresh", "")
        .apply()

    override suspend fun getMyProducts(status: String)=localDataApi.getMyProducts(status)

    override suspend fun saveMyProducts(productsDao: MyProductsDao) =localDataApi.saveMyProducts(productsDao)

    override suspend fun removeMyProducts()=localDataApi.removeMyProducts()
    override suspend fun getMyTenders(status:String)=localDataApi.getMyTenders(status)
    override suspend fun saveMyTenders(tendersDao: MyTendersDao) =localDataApi.saveMyTenders(tendersDao)
    override suspend fun removeMyTenders() =localDataApi.removeMyTenders()
    override suspend fun getFavoritesTender()=localDataApi.getFavoritesTender()

    override suspend fun saveFavoritesTender(list: List<FavoriteTender>) =localDataApi.saveFavoritesTender(list.map { it.toDao })
    override suspend fun removeFavoritesTender() =localDataApi.removeFavorites()
     override suspend fun getPersonalSelection()=localDataApi.getPersonalSelections().map { SelectionModel(
         it.name?:"",it.count?:0,it.id,it.products?: listOf(),it.date_create) }

     override suspend fun savePersonalSelections(items: List<SelectionModel>) =localDataApi
         .savePersonalSelections(items.map { SelectionPersonal(it.name,it.count,it.id,it.products,it.date_create) })

     override suspend fun removePersonalSelections() =localDataApi.removePersonalSelections()

     override suspend fun getChats(): List<ChatDao> =localDataApi.getChats()
    override suspend fun saveChats(chats: List<MainChat>) = localDataApi.saveChats(chats.map { it.toDao})
    override suspend fun removeChats() =localDataApi.removeChats()
    override suspend fun getProfile(): ProfileDao =localDataApi.getProfile()

    override suspend fun saveProfile(model: ProfileDao) = localDataApi.saveProfile(model)

    override fun deleteProfile() =localDataApi.deleteProfile()

    override suspend fun getSelection(): List<SelectionDao> =localDataApi.getSelection()
    override suspend fun getSelectionById(id: String): SelectionDao {
        TODO("Not yet implemented")
    }

    override suspend fun getPersonalSelectionById(id: String): SelectionDao {
        TODO("Not yet implemented")
    }

    override suspend fun saveSelections(selections: List<SelectionModel>) =localDataApi.saveSelections(selections.map {it.toDao  })

    override suspend fun removeSelections() =localDataApi.removeSelections()
    override suspend fun getAdvertising(): List<AdvertisingDao> =localDataApi.getAdvertising()

    override suspend fun saveAdvertising(advertising: List<CategoryAdvertisingModel>) =localDataApi.saveAdvertising(advertising.map { it.toDao })

    override suspend fun removeAdvertising() =localDataApi.removeAdvertising()

    override suspend fun getFavoritesProduct(): List<FavoriteProductDao> = localDataApi.getFavoritesProduct()

    override suspend fun saveFavorites(list: List<FavoriteProduct>) = localDataApi.saveFavorites(list.map { it.toDao })

    override suspend fun removeFavorites() =localDataApi.removeFavorites()
    override suspend fun getCategories() =localDataApi.getCategories()

    override suspend fun saveCategories(categories:List<ProductCategoryModel>) =localDataApi.saveCategories(categories.map { it.toDao })

    override suspend fun removeCategories() =localDataApi.removeCategories()


}


