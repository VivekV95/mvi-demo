package com.example.mvidemo.ui

import com.example.mvidemo.util.DataState

interface DataStateListener {

    fun onDataStateChanged(dataState: DataState<*>?)
}