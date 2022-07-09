package com.expostore.db

import android.content.Context
import androidx.room.*
import com.expostore.db.enities.AdvertisingDao
import com.expostore.db.enities.ProfileDao

import com.expostore.db.enities.TokenDao
import com.expostore.db.enities.chat.*
import com.expostore.db.enities.product.ProductDao

import com.expostore.db.enities.selection.SelectionDao

@Database(entities = [
    TokenDao::class,
  ChatDao::class, ProfileDao::class,SelectionDao::class,AdvertisingDao::class
          ], version = 1, exportSchema = false)
@TypeConverters(InfoTypeConverter::class,ConvertMessage::class,

    ConvertChat::class,ConvertItem::class,
  ConvertFile::class,ConvertUser::class,ConvertImage::class,ConvertProduct::class,ConverterProfile::class,
    ConverterShop::class,ConvertProductModel::class)
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