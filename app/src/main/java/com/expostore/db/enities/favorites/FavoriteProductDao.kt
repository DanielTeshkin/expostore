package com.expostore.db.enities.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.product.ProductModel

@Entity(tableName = "favorite")
 data class FavoriteProductDao(
    @PrimaryKey
    @ColumnInfo(name = "id") val id:String,
    @ColumnInfo(name = "product") val product: ProductModel,
    @ColumnInfo(name = "notes")  val notes: String?,
    @ColumnInfo(name = "user") val user: String?
 )