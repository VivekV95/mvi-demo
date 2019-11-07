package com.example.mvidemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User
import com.example.mvidemo.repository.Repository
import com.example.mvidemo.ui.main.state.MainStateEvent
import com.example.mvidemo.ui.main.state.MainViewState
import com.example.mvidemo.util.AbsentLiveData

class MainViewModel() : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> =
        Transformations
            .switchMap(_stateEvent) { stateEvent ->
                stateEvent?.let {
                    Log.d("Test", "Inside dataState switchMap(), stateEvent has been changed")
                    handleStateEvent(it)
                }
            }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> =
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
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User) {
        Log.d("Test", "Inside setUser(), changing viewState")
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        Log.d("Test", "Inside getCurrentViewStateOrNew()")
        return viewState.value?.let {
            it
        }?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        Log.d("Test", "Setting state event inside ViewModel")
        _stateEvent.value = event
    }
}
