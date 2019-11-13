package com.example.mvidemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User
import com.example.mvidemo.repository.Repository
import com.example.mvidemo.ui.BaseViewModel
import com.example.mvidemo.ui.main.state.MainStateEvent
import com.example.mvidemo.ui.main.state.MainViewState
import com.example.mvidemo.util.AbsentLiveData
import com.example.mvidemo.util.DataState

class MainViewModel : BaseViewModel<MainStateEvent, MainViewState>() {

    override fun initNewViewState(): MainViewState {
        return MainViewState()
    }

    override fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> =
        when (stateEvent) {
            is MainStateEvent.GetBlogPostsEvent -> {
                Log.d("Test", "Inside handleStateEvent(), getting blog posts")
                Repository.getBlogPosts()
            }
            is MainStateEvent.GetUserEvent -> {
                Log.d("Test", "Inside handleStateEvent(), getting user")
                Repository.getUser(stateEvent.userId)
            }
            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        Log.d("Test", "Inside setBlogListData(), changing viewState")
        val update = getCurrentViewStateOrNew().copy(blogPosts = blogPosts)
        _viewState.value = update
    }

    fun setUser(user: User) {
        Log.d("Test", "Inside setUser(), changing viewState")
        val update = getCurrentViewStateOrNew().copy(user = user)
        _viewState.value = update
    }
}
