package com.example.mvidemo.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mvidemo.R
import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User
import com.example.mvidemo.ui.DataStateListener
import com.example.mvidemo.ui.main.state.MainStateEvent
import com.example.mvidemo.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException
import java.lang.Exception

class MainFragment: Fragment(), BlogListAdapter.Interaction {

    override fun onItemSelected(position: Int, item: BlogPost) {
        Toast.makeText(activity, item.body, Toast.LENGTH_SHORT).show()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var dataStateListener: DataStateListener

    private lateinit var blogListAdapter: BlogListAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            blogListAdapter = BlogListAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
    }

    private fun setUserProperties(user: User) {
        email.text = user.email
        username.text = user.username

        view?.let {
            Glide.with(it.context)
                .load(user.image)
                .into(image)
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            Log.d("Test", "Inside dataState Observer: $dataState" )

            dataStateListener.onDataStateChanged(dataState)

            dataState.data?.let {event ->

                event.getContentIfNotHandled()?.let {mainViewState ->
                    mainViewState.blogPosts?.let {blogPosts ->
                        viewModel.setBlogListData(blogPosts)
                    }

                    mainViewState.user?.let {user ->
                        viewModel.setUser(user)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState ->
            viewState.blogPosts?.let {blogList ->
                Log.d("Test", "Inside viewState Observer, setting Blog posts to RV: $blogList" )
                blogListAdapter.submitList(blogList)
            }

            viewState.user?.let {user ->
                Log.d("Test", "Inside viewState Obersver, setting user data: $user" )
                setUserProperties(user)
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            Log.d("test", "$context must implement DataStateListener")
        }
    }
}