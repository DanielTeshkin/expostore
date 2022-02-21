package com.expostore.ui.main.interactor

import com.expostore.api.ApiWorker
import com.expostore.api.ApiWorkerImpl
import com.expostore.ui.base.BaseInteractor
import javax.inject.Inject

/**
 * @author Fedotov Yakov
 */
class MainInteractor @Inject constructor(private val apiWorker: ApiWorker): BaseInteractor() {

}