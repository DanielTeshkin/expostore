package com.expostore.data.remote.api.base

import com.google.gson.GsonBuilder


abstract class BaseApiResponse<T> {
    open var result: T? = null

    companion object {
        internal val gson = GsonBuilder().create()
    }
}


