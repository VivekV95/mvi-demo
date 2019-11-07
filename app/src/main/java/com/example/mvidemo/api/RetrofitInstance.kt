package com.example.mvidemo.api

import com.example.mvidemo.util.LiveDataCallAdapter
import com.example.mvidemo.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    const val BASE_URL = "https://open-api.xyz/"
    val retrofitInstance by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    val apiService by lazy {
        retrofitInstance.create(ApiService::class.java)
    }
}