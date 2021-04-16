package com.coolweather.coolweatherjetpack.data.network

import com.coolweather.coolweatherjetpack.BuildConfig
import com.coolweather.coolweatherjetpack.util.LogUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object ServiceCreator {

    private const val BASE_URL = "http://guolin.tech/"

    private val httpClient = OkHttpClient.Builder()


    fun <T> create(serviceClass: Class<T>): T {
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(getLogHttpLoggingInterceptor())
        }
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()

        return retrofit.create(serviceClass)
    }


    private fun getLogHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            LogUtil.longInfo("network", message)
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}