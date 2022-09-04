package com.expostore.model.category

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.productcategory.Parent
import com.expostore.data.remote.api.pojo.productcategory.ProductCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCategoryModel( val id: String="",
                                 val name: String?="",
                                val sortingNumber: Int?=0,
                                val parent: ParentModel?=null,
                                 val child_category: List<ProductCategoryModel>? = null,
                                  val have_child:Boolean = false) :Parcelable

val ProductCategory.toModel:ProductCategoryModel
get() = ProductCategoryModel(id?:"", name, sortingNumber, parent?.toModel, child_category?.map { it.toModel }, have_child)
@Parcelize
data class ParentModel(
     val id : String?  = null,
    val name : String?  = null,
     val sortingNumber : Int?     = null,
     var haveChild: Boolean? = null,
    var parent: ParentModel?  = null
):Parcelable
val Parent.toModel:ParentModel
get() = ParentModel(id, name, sortingNumber, haveChild,parent?.toModel)