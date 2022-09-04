package com.expostore.data.remote.api.pojo.productcategory

import com.google.gson.annotations.SerializedName

data class ProductCategory(
    @SerializedName("id") val id: String?="",
    @SerializedName("name") val name: String?="",
    @SerializedName("sorting_number") val sortingNumber: Int?=0,
    @SerializedName("parent") val parent: Parent?=null,
    @SerializedName("child_category") val child_category: List<ProductCategory>? = null,
    @SerializedName("have_child")  val have_child:Boolean = false

    )
data class Parent (

    @SerializedName("id"             ) var id            : String?  = null,
    @SerializedName("name"           ) var name          : String?  = null,
    @SerializedName("sorting_number" ) var sortingNumber : Int?     = null,
    @SerializedName("have_child"     ) var haveChild     : Boolean? = null,
    @SerializedName("parent"         ) var parent        : Parent?  = null

)

