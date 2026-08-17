package com.sean.jikananimelist.network

import com.sean.jikananimelist.model.remote.RemoteJAnimeResponse
import com.sean.jikananimelist.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface JAnimeClient {
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query(value = "filter") filter: String? = null,
        @Query(value = "page") pageNumber: Int,
        @Query(value = "limit") pageSize: Int = 25
    ): RemoteJAnimeResponse

    companion object {
        fun create(): JAnimeClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(Constants.baseUrl)
                .build()
                .create<JAnimeClient>()
        }
    }
}