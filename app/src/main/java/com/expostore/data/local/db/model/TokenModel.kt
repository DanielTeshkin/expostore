package com.expostore.data.local.db.model

import com.expostore.data.local.db.enities.TokenDao


data class TokenModel(
    val refresh: String?="",
     val access: String?=""
 )
val TokenDao.toModel
    get()=TokenModel(
        refresh=refresh?:"",
        access=access?:""
    )
val TokenModel.toDao
get() = TokenDao( refresh=refresh, access = access)
