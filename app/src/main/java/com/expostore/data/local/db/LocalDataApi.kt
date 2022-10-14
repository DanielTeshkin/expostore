package com.expostore.data.local.db

import androidx.room.*
import com.expostore.data.local.db.enities.*

import com.expostore.data.local.db.enities.chat.ChatDao
import com.expostore.data.local.db.enities.favorites.FavoriteProductDao
import com.expostore.data.local.db.enities.favorites.FavoriteTenderDao

import com.expostore.data.local.db.enities.selection.SelectionDao


@Dao
interface LocalDataApi {
    @Query("Select * from token")
     fun getToken():TokenDao?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToken(tokenDao: TokenDao)

    @Query("DELETE FROM token")
    suspend fun removeToken()

    @Query("Select * from chats")
    suspend fun getChats():List<ChatDao>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveChats(chats:List<ChatDao>)

    @Query("DELETE FROM chats")
    suspend fun removeChats()

    @Query("Select * from profile")
    suspend fun getProfile(): ProfileDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(model: ProfileDao)

    @Query("DELETE FROM profile")
    fun deleteProfile()

    @Query("Select * from selection")
    suspend fun getSelection():List<SelectionDao>

    @Query("SELECT * FROM selection WHERE id=:id ")
    suspend fun getSelectionById(id:String):SelectionDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSelections(selections:List<SelectionDao>)

    @Query("DELETE FROM selection")
    suspend fun removeSelections()

    @Query("Select * from advertising")
    suspend fun getAdvertising():List<AdvertisingDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAdvertising(advertising:List<AdvertisingDao>)

    @Query("DELETE FROM selection")
    suspend fun removeAdvertising()

    @Query("Select * from favorite")
    suspend fun getFavoritesProduct():List<FavoriteProductDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorites(list: List<FavoriteProductDao>)

    @Query("DELETE FROM favorite")
    suspend fun removeFavorites()

    @Query("Select * from favorite_tenders")
    suspend fun getFavoritesTender():List<FavoriteTenderDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoritesTender(list: List<FavoriteTenderDao>)

    @Query("DELETE FROM favorite_tenders")
    suspend fun removeFavoritesTender()

    @Query("Select * from category")
    suspend fun getCategories():List<CategoryDao>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(list: List<CategoryDao>)

    @Query("DELETE FROM category")
    suspend fun removeCategories()

    @Query("Select * from personal")
    suspend fun getPersonalSelections():List<SelectionPersonal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePersonalSelections(list: List<SelectionPersonal>)

    @Query("DELETE FROM personal")
    suspend fun removePersonalSelections()

    @Query("Select * from my_products WHERE status LIKE :status ORDER BY status ASC")
   suspend fun getMyProducts(status: String):MyProductsDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMyProducts(productsDao: MyProductsDao)

    @Query("DELETE FROM my_products")
    suspend fun removeMyProducts()

    @Query("Select * from my_tenders WHERE status LIKE :status ORDER BY status ASC")
    suspend fun getMyTenders(status:String):MyTendersDao

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMyTenders(tendersDao: MyTendersDao)

    @Query("DELETE FROM my_tenders")
    suspend fun removeMyTenders()




}