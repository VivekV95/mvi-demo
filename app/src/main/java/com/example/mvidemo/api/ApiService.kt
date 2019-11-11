package com.example.mvidemo.api

import androidx.lifecycle.LiveData
import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User
import com.example.mvidemo.util.GenericApiResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): Flowable<Response<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): Flowable<Response<List<BlogPost>>>

}

