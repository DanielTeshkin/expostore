package com.expostore.db.enities.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.pojo.getchats.ProductResponse
import com.expostore.db.enities.chat.ImageDao
import com.expostore.db.enities.chat.toDao

@Entity
 data class ProductDao(
 @PrimaryKey
 @ColumnInfo(name = "id") val id : String ,
 @ColumnInfo(name="name"   ) val name   : String="",
 @ColumnInfo(name="image_product" ) val images : List<ImageDao>
 )

val ProductResponse.toDao : ProductDao
get() = ProductDao(id?:"",name?:"",images.map { it.toDao })
