package com.expostore.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.response.CategoryAdvertisingResponse
import com.expostore.model.category.CategoryAdvertisingModel
import com.google.gson.annotations.SerializedName
@Entity(tableName = "advertising")
class AdvertisingDao(
    @ColumnInfo(name="image")
    val image: String? = null,

    @ColumnInfo(name="dateCreated")
    val dateCreated: String? = null,

    @PrimaryKey
    @ColumnInfo(name="id")
    val id: String,

    @ColumnInfo(name="url")
    val url: String? = null
)
val CategoryAdvertisingResponse.toDao :AdvertisingDao
get() = AdvertisingDao(image, dateCreated, id?:"bbb", url)
val CategoryAdvertisingModel.toDao :AdvertisingDao
get() = AdvertisingDao(image, dateCreated, id, url)

val AdvertisingDao.toModel:CategoryAdvertisingModel
get() = CategoryAdvertisingModel(image?:"",dateCreated?:"",id?:"bbb",url?:"")