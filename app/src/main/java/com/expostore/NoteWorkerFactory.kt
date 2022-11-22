package com.expostore

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

import com.expostore.data.local.db.LocalWorker
import com.expostore.data.remote.api.ApiWorker
import dagger.Lazy
import javax.inject.Inject

class MyWorkerFactory  @Inject constructor( private val apiWorker: Lazy<ApiWorker>,private val localWorker: Lazy<LocalWorker>):WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker =
        if (workerClassName==NoteWorker::class.java.name) NoteWorker(appContext,workerParameters,apiWorker)
      else MessagingWorker(appContext,workerParameters,apiWorker,localWorker)

}