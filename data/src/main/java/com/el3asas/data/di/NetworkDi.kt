package com.el3asas.data.di

import com.el3asas.data.BuildConfig
import com.el3asas.data.network.apis.NewsApis
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkDi {

    @Provides
    @Singleton
    fun providesRetrofit(builder: OkHttpClient.Builder): Retrofit = Retrofit.Builder()
        .client(builder.build())
        .baseUrl(BuildConfig.BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMainOkhttp(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().addInterceptor(
            LoggingInterceptor
                .Builder()
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request(" ")
                .response(" ")
                .addQueryParam("apiKey", BuildConfig.ApiKey)
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideNewsApis(retrofit: Retrofit): NewsApis = retrofit.create(NewsApis::class.java)

}