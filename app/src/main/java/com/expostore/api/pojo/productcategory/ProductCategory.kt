package com.expostore.api.pojo.productcategory

import android.os.Parcel
import android.os.Parcelable
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

data class ProductCategory(
        @JsonProperty("id") val id: String?,
        @JsonProperty("name") val name: String?,
        @JsonProperty("sorting_number") val sortingNumber: Int?,
        @JsonProperty("parent") val parent: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeValue(sortingNumber)
        parcel.writeString(parent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductCategory> {
        override fun createFromParcel(parcel: Parcel): ProductCategory {
            return ProductCategory(parcel)
        }

        override fun newArray(size: Int): Array<ProductCategory?> {
            return arrayOfNulls(size)
        }
    }
}