package com.example.mvidemo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvidemo.api.RetrofitInstance
import com.example.mvidemo.ui.main.state.MainViewState
import com.example.mvidemo.util.ApiEmptyResponse
import com.example.mvidemo.util.ApiErrorResponse
import com.example.mvidemo.util.ApiSuccessResponse
import com.example.mvidemo.util.DataState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> =
        Transformations.switchMap(RetrofitInstance.apiService.getBlogPosts()) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                message = null,
                                data = MainViewState(apiResponse.body)
                            )
                        }
                        is ApiErrorResponse -> {
                            value = DataState.error(message = apiResponse.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            value = DataState.error("Returned nothing")
                        }
                    }
                }
            }
        }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> =
        Transformations.switchMap(RetrofitInstance.apiService.getUser(userId)) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState.data(
                                null,
                                MainViewState(
                                    user = apiResponse.body
                                )
                            )
                        }

                        is ApiErrorResponse -> {
                            value = DataState.error(apiResponse.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            value = DataState.error("Returned nothing")
                        }
                    }
                }
            }
        }
}