package com.expostore.data.remote.api.response

import com.expostore.data.remote.api.pojo.gettenderlist.Tender
import com.google.gson.annotations.SerializedName

data class TenderFavoriteResponse(
    @SerializedName("id"     ) var id     : String? = null,
    @SerializedName("tender" ) var tender : Tender = Tender(),
    @SerializedName("notes"  ) var notes  : String? = null,
    @SerializedName("user"   ) var user   : String? = null
)
