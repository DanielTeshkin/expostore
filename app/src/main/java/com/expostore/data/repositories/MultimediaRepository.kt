package com.expostore.data.repositories

import com.expostore.api.ApiWorker
import com.expostore.api.pojo.getchats.ResponseFile
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MultimediaRepository @Inject constructor( private val apiWorker: ApiWorker): BaseRepository() {

    fun saveFile(multipartBody: MultipartBody.Part, name: RequestBody) = flow {
        val result=handleOrDefault(ResponseFile()){apiWorker.fileCreate(multipartBody,name)}
        emit(result)
    }

    fun saveImage(saveImageRequestData: List<SaveImageRequestData>)=
        flow{
            val result=handleOrDefault(SaveImageResponseData()) { apiWorker.saveImage(saveImageRequestData)}
            emit(result)
        }

}