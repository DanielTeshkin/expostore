package com.expostore.model.category

import android.os.Parcelable
import com.expostore.data.local.db.enities.CategoryDao
import com.expostore.data.remote.api.pojo.productcategory.Parent
import com.expostore.data.remote.api.pojo.productcategory.ProductCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCategoryModel(val id: String="",
                                val name: String?="",
                                val sortingNumber: Int?=0,
                                val child_category: List<ProductCategoryModel> = listOf(),
                                val have_child:Boolean = false) :Parcelable

val ProductCategory.toModel:ProductCategoryModel
get() = ProductCategoryModel(id?:"", name, sortingNumber,  child_category?.map { it.toModel }?: listOf(), have_child)
@Parcelize
data class ParentModel(
     val id : String  = "",
    val name : String  = "",
     val sortingNumber : Int     = 0,
     var haveChild: Boolean = false,
    var parent: ParentModel  = ParentModel()
):Parcelable
val Parent.toModel:ParentModel
get() = ParentModel(id?:"", name?:"", sortingNumber?:0, haveChild?:false,parent?.toModel?: ParentModel())

val CategoryDao.toModel : ProductCategoryModel
get() = ProductCategoryModel(id, name, sortingNumber,  child_category?: listOf(), have_child)