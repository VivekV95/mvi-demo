package com.example.mvidemo.repository

import android.net.Network
import androidx.lifecycle.LiveData
import com.example.mvidemo.api.RetrofitInstance
import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User
import com.example.mvidemo.ui.main.state.MainViewState
import com.example.mvidemo.util.ApiSuccessResponse
import com.example.mvidemo.util.DataState
import com.example.mvidemo.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(data = MainViewState(blogPosts = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitInstance.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(data = MainViewState(user = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitInstance.apiService.getUser(userId)
            }

        }.asLiveData()
    }
}