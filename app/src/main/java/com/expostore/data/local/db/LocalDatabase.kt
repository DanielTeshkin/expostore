package com.expostore.data.local.db

import android.content.Context
import androidx.room.*
import com.expostore.data.local.db.enities.*
import com.expostore.data.local.db.enities.chat.*
import com.expostore.data.local.db.enities.favorites.FavoriteProductDao
import com.expostore.data.local.db.enities.favorites.FavoriteTenderDao
import com.expostore.data.local.db.enities.selection.SelectionDao


@Database(entities = [TokenDao::class,
  ChatDao::class, ProfileDao::class,
    SelectionDao::class,
    AdvertisingDao::class,
    UserDao::class,
    FavoriteProductDao::class,
   SelectionPersonal::class,
CategoryDao::class,
MyProductsDao::class,
MyTendersDao::class,
FavoriteTenderDao::class], version = 1,exportSchema =false)
@TypeConverters(InfoTypeConverter::class,ConvertMessage::class, ConvertChat::class,ConvertItem::class,
  ConvertFile::class,ConvertUser::class,ConvertImage::class,ConvertProduct::class,ConverterProfile::class,
    ConverterShop::class,ConvertProductModel::class,
    ConvertUserDaoModel::class,
    ConvertCategoryProductModel::class,
    ConvertParentModel::class,
ConvertTenderModel::class)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun getDao(): LocalDataApi
    companion object {
        @Volatile private var instance: LocalDatabase? = null
        fun getDatabase(context: Context): LocalDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, LocalDatabase::class.java, "expostore")
                .fallbackToDestructiveMigration()
                .build()
    }
}