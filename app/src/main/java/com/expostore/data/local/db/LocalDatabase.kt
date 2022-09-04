package com.expostore.data.local.db

import android.content.Context
import androidx.room.*
import com.expostore.data.local.db.enities.AdvertisingDao
import com.expostore.data.local.db.enities.ProfileDao

import com.expostore.data.local.db.enities.TokenDao
import com.expostore.data.local.db.enities.chat.*
import com.expostore.data.local.db.enities.favorites.FavoriteProductDao

import com.expostore.data.local.db.enities.selection.SelectionDao

@Database(entities = [
    TokenDao::class,
  ChatDao::class, ProfileDao::class,SelectionDao::class,AdvertisingDao::class,UserDao::class,FavoriteProductDao::class
          ], version = 1, autoMigrations = [AutoMigration (from = 1, to = 2)],exportSchema =true)
@TypeConverters(InfoTypeConverter::class,ConvertMessage::class, ConvertChat::class,ConvertItem::class,
  ConvertFile::class,ConvertUser::class,ConvertImage::class,ConvertProduct::class,ConverterProfile::class,
    ConverterShop::class,ConvertProductModel::class,ConvertUserDaoModel::class)
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