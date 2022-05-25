package com.expostore.data
import com.expostore.api.ApiWorker

import com.expostore.ui.base.BaseInteractor
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class LocalRepository @Inject constructor(private val localDataApi: LocalDataApi, val apiWorker: ApiWorker):BaseInteractor() {



}


