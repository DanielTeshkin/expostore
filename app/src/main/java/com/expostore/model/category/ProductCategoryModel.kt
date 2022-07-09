package com.expostore.model.category

import android.os.Parcelable
import com.expostore.api.pojo.productcategory.ProductCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCategoryModel( val id: String="",
                                 val name: String?="",
                                val sortingNumber: Int?=0,
                                val parent: String?="",
                                 val child_category: List<ProductCategoryModel>? = null,
                                  val have_child:Boolean = false) :Parcelable

val ProductCategory.toModel:ProductCategoryModel
get() = ProductCategoryModel(id?:"", name, sortingNumber, parent, child_category?.map { it.toModel }, have_child)
