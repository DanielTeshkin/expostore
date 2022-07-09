package com.expostore.ui.general

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getchats.ChatResponse
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.db.LocalWorker
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserActivityInteractor @Inject constructor(private val apiWorker: ApiWorker, private val localWorker: LocalWorker) :
    BaseInteractor() {
    fun updateSelected(id:String)= flow{
        val result=handleOrDefault(SelectFavoriteResponseData("","","","")){apiWorker.selectFavorite(id)}
        emit(result)
    }


}