package com.expostore.model.tender

import android.os.Parcelable
import com.expostore.data.remote.api.pojo.gettenderlist.ParentTender
import com.expostore.data.remote.api.pojo.gettenderlist.TenderCategory
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TenderCategoryModel( val id: String,
                               val name: String?,
                               val sortingNumber: Int?,
                                val have_child:Boolean?,
                              val parent: ParentTenderModel?):Parcelable

val TenderCategory.toModel : TenderCategoryModel
get() = TenderCategoryModel(
    id, name, sortingNumber, have_child,parent?.toModel
)
@Parcelize
data class ParentTenderModel(
    val id: String,
    val name: String?,
    val sortingNumber: Int?,
    val have_child:Boolean?,
    val parent:ParentTenderModel?
):Parcelable

val ParentTender.toModel:ParentTenderModel
get() = ParentTenderModel(id,name,sortingNumber,have_child,parent?.toModel)