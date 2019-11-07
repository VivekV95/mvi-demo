package com.example.mvidemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User
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
                    handleStateEvent(it)
                }
            }

    fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> =
        when (stateEvent) {
            is MainStateEvent.GetBlogPostsEvent -> {
                AbsentLiveData.create()
            }
            is MainStateEvent.GetUserEvent -> {
                AbsentLiveData.create()
            }
            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }

    fun setBlogListData(blogPosts: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let {
            it
        }?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}
