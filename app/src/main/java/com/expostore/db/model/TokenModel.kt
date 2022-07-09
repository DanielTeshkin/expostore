package com.expostore.db.model

import com.expostore.db.enities.TokenDao


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
