package com.expostore.data.local.db.enities.selection

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.data.remote.api.response.SelectionResponse


import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.model.product.toModel

@Entity(tableName = "selection")
data class SelectionDao(
    @ColumnInfo(name="name")
    val name: String? = null,
    @ColumnInfo(name="count")
    val count: Int? = null,
    @PrimaryKey
    @ColumnInfo(name="id") val id : String,
    @ColumnInfo(name="products")
    val products: List<ProductModel>? = null,
    @ColumnInfo(name="date_create")
    val date_create: String? = null
)
val SelectionResponse.toDao:SelectionDao
get() = SelectionDao(name, count, id?:"", products?.map { it.toModel }, date_create)
val SelectionModel.toDao:SelectionDao
get() = SelectionDao(name, count, id, products, date_create)

val SelectionDao.toModel:SelectionModel
get() =SelectionModel(name?:"",count?:0,id,products?: listOf())


@Entity(tableName = "personal")
data class SelectionPersonal(
    @ColumnInfo(name="name")
    val name: String? = null,
    @ColumnInfo(name="count")
    val count: Int? = null,
    @PrimaryKey
    @ColumnInfo(name="id") val id : String,
    @ColumnInfo(name="products")
    val products: List<ProductModel>? = null,
    @ColumnInfo(name="date_create")
    val date_create: String? = null
)