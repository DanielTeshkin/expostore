package com.expostore

import android.app.Application
import com.expostore.api.Retrofit
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Fedotov Yakov
 */
@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        // TODO: убрать, когда будет реализации с даггером
        Retrofit.build(applicationContext)
    }
}