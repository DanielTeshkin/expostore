package com.expostore.data.repositories

import com.expostore.data.remote.api.ApiWorker
import com.expostore.data.remote.api.pojo.getchats.ResponseFile
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.local.db.LocalWorker
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.data.remote.api.pojo.saveimage.SaveFileResponseData
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MultimediaRepository @Inject constructor(private val apiWorker: ApiWorker, private  val localWorker: LocalWorker): BaseRepository() {

    fun saveFile(multipartBody: MultipartBody.Part, name: RequestBody) = flow {
        val result=handleOrDefault(ResponseFile()){apiWorker.fileCreate(multipartBody,name)}
        emit(result)
    }

    fun saveImage(saveImageRequestData: List<SaveImageRequestData>)=
        flow{
            val result=handleOrDefault(SaveImageResponseData()) { apiWorker.saveImage(saveImageRequestData)}
            emit(result)
        }
    fun saveFileBase64(requestData: List<SaveFileRequestData>) = flow {
        val result=handleOrDefault(SaveFileResponseData()){apiWorker.saveFileBase64(requestData)}
        emit(result)
    }

}