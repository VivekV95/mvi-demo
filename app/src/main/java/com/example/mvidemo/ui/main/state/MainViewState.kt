package com.example.mvidemo.ui.main.state

import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)