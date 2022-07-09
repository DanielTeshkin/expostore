package com.expostore.api.base

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.GsonBuilder
import retrofit2.Response


/**
 * @author Fedotov Yakov
 */
abstract class BaseApiResponse<T> {
    open var result: T? = null

    companion object {
        internal val gson = GsonBuilder().create()
    }
}


