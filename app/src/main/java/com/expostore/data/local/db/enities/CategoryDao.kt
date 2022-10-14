package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.category.ParentModel
import com.expostore.model.category.ProductCategoryModel
import com.expostore.model.product.ProductModel

@Entity(tableName = "category")
class CategoryDao(
    @PrimaryKey
    @ColumnInfo(name = "id") val id:String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sortingNumber")   val sortingNumber: Int?=0,
    @ColumnInfo(name = "child_category")  val child_category: List<ProductCategoryModel>? = listOf(),
    @ColumnInfo(name = "have_child") val have_child:Boolean = false
)
val ProductCategoryModel.toDao:CategoryDao
get() = CategoryDao(id,name?:"",sortingNumber, child_category, have_child)