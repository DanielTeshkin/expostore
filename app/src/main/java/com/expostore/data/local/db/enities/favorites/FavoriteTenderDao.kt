package com.expostore.data.local.db.enities.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.favorite.FavoriteProduct
import com.expostore.model.favorite.FavoriteTender
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel

@Entity(tableName = "favorite_tenders")
data class FavoriteTenderDao(
    @PrimaryKey
    @ColumnInfo(name = "id") val id:String,
    @ColumnInfo(name = "product") val tender: TenderModel,
    @ColumnInfo(name = "notes")  val notes: String?,
    @ColumnInfo(name = "user") val user: String?
)

val FavoriteTender.toDao :FavoriteTenderDao
    get() = FavoriteTenderDao(
        id?:"",tender, notes, user
    )