package com.expostore.db

import android.content.Context
import androidx.room.*
import com.expostore.data.InfoChat
import com.expostore.data.InfoTypeConverter
import com.expostore.data.LocalDataApi

@Database(entities = [InfoChat::class], version = 1, exportSchema = true)
@TypeConverters(InfoTypeConverter::class)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun getDao():LocalDataApi
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