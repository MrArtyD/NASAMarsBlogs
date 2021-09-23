package com.example.nasamarsblogs.ui

import androidx.lifecycle.ViewModel
import com.example.nasamarsblogs.api.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(private val api: Api) : ViewModel() {

}