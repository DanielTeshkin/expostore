package com.expostore

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider{
    @Inject
    lateinit var workerFactory: MyWorkerFactory
    override fun getWorkManagerConfiguration()=
        Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

}