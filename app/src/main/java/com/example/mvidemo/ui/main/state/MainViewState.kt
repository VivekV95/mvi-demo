package com.example.mvidemo.ui.main.state

import com.example.mvidemo.model.BlogPost
import com.example.mvidemo.model.User

data class MainViewState(
    val blogPosts: List<BlogPost>? = null,
    val user: User? = null
)