package com.expostore.data
import javax.inject.Inject

class LocalRepository @Inject constructor(private val localDataApi: LocalDataApi) {

    fun insertInfo(infoChat: InfoChat){
        localDataApi.saveChatInfo(infoChat)
    }

    fun getInfoChat():InfoChat= localDataApi.getChatInfo()

}