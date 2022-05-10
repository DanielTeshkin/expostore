package com.expostore.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.expostore.BuildConfig
import com.expostore.api.ApiWorker
import com.expostore.api.ApiWorkerImpl
import com.expostore.api.Interceptor
import com.expostore.api.ServerApi
import com.expostore.data.LocalDataApi
import com.expostore.db.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    // TODO: поменять context на репозиторий, когда будет бд
    @Provides
    fun provideHttpClient(@ApplicationContext context: Context, interceptor: Interceptor):OkHttpClient  {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY )

      return  OkHttpClient.Builder()
            .addInterceptor(interceptor).
              addInterceptor(loggingInterceptor)
          .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()}

    @Singleton
    @Provides
    fun provideServerApi(retrofit: Retrofit): ServerApi =
        retrofit.create(ServerApi::class.java)

    @Singleton
    @Provides
    fun provideApiWorker(serverApi: ServerApi, @ApplicationContext context: Context): ApiWorker =
        ApiWorkerImpl(serverApi, context)

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context)=LocalDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideLocalApi(localDatabase: LocalDatabase):LocalDataApi=localDatabase.getDao()
}