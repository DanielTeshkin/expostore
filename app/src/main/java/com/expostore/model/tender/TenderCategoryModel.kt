package com.expostore.model.tender

import android.os.Parcelable
import com.expostore.api.pojo.gettenderlist.TenderCategory
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TenderCategoryModel( val id: String,
                               val name: String?,
                               val sortingNumber: Int?,
                                val have_child:Boolean?,
                              val parent: String?):Parcelable

val TenderCategory.toModel : TenderCategoryModel
get() = TenderCategoryModel(
    id, name, sortingNumber, have_child,parent
)
