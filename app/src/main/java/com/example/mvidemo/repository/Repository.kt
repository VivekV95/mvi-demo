package com.example.mvidemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvidemo.api.RetrofitInstance
import com.example.mvidemo.ui.main.state.MainViewState
import com.example.mvidemo.util.ApiEmptyResponse
import com.example.mvidemo.util.ApiErrorResponse
import com.example.mvidemo.util.ApiSuccessResponse

object Repository {

    fun getBlogPosts(): LiveData<MainViewState> =
        Transformations.switchMap(RetrofitInstance.apiService.getBlogPosts()) { apiResponse ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = MainViewState(
                                blogPosts = apiResponse.body
                            )
                        }
                        is ApiErrorResponse -> {
                            value = MainViewState()
                        }
                        is ApiEmptyResponse -> {
                            value = MainViewState()
                        }
                    }
                }
            }
        }

    fun getUser(userId: String): LiveData<MainViewState> =
        Transformations.switchMap(RetrofitInstance.apiService.getUser(userId)) { apiResponse ->
            object : LiveData<MainViewState>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = MainViewState(
                                user = apiResponse.body
                            )
                        }

                        is ApiErrorResponse -> {
                            value = MainViewState()
                        }
                        is ApiEmptyResponse -> {
                            value = MainViewState()
                        }
                    }
                }
            }
        }
}