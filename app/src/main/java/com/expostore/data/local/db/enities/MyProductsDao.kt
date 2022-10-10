package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.product.ProductModel

@Entity(tableName = "my_products")
data class MyProductsDao(
    @PrimaryKey
    @ColumnInfo(name = "status") val status:String="",
    @ColumnInfo(name = "items") val items:List<ProductModel>,
)