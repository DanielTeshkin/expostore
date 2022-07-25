package com.expostore.ui.general

import com.expostore.api.ApiWorker
import com.expostore.db.LocalWorker
import javax.inject.Inject

class UserActivityInteractor @Inject constructor(private val apiWorker: ApiWorker, private val localWorker: LocalWorker) :
    BaseInteractor() {



}