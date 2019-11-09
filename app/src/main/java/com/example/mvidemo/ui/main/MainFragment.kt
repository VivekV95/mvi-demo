package com.example.mvidemo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvidemo.R
import com.example.mvidemo.ui.main.state.MainStateEvent
import java.lang.Exception

class MainFragment: Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?: throw Exception("Invalid Activity")

        subscribeObservers()
    }

    fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            Log.d("Test", "Inside dataState Observer: $dataState" )

            dataState.data?.let {mainViewState ->
                mainViewState.blogPosts?.let {blogPosts ->
                    viewModel.setBlogListData(blogPosts)
                }

                mainViewState.user?.let {user ->
                    viewModel.setUser(user)
                }
            }

            dataState.message?.let {

            }

            dataState.loading.let {
                
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            viewState.blogPosts?.let {
                Log.d("Test", "Inside viewState Observer, setting Blog posts to RV: $it" )
            }

            viewState.user?.let {
                Log.d("Test", "Inside viewState Obersver, setting user data: $it" )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()

            R.id.action_get_blog -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogsEvent() {
        Log.d("Test", "triggerGetBlogsEvent() called, changing MainStateEvent")
        viewModel.setStateEvent(MainStateEvent.GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        Log.d("Test", "triggerGetUserEventCalled() called, changing MainStateEvent")
        viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }
}