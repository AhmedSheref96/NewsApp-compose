package com.el3asas.data.network.apis

import com.el3asas.domain.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApis {

    @GET("top-headlines")
    suspend fun getHomeNews(
        @Query("page") page: Int,
        @Query("country") country: String = "us"
    ): NewsResponse

    @GET("everything")
    suspend fun getSearchNews(
        @Query("page") page: Int,
        @Query("q") search: String = ""
    ): NewsResponse

}