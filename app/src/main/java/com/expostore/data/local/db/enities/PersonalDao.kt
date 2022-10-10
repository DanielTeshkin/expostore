package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.product.ProductModel

@Entity(tableName = "personal")
data class SelectionPersonal(
    @ColumnInfo(name="name")
    val name: String? = "",
    @ColumnInfo(name="count")
    val count: Int? = 0,
    @PrimaryKey
    @ColumnInfo(name="id") val id : String,
    @ColumnInfo(name="products")
    val products: List<ProductModel>? = listOf(),
    @ColumnInfo(name="date_create")
    val date_create: String? = ""
)