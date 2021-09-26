package com.example.nasamarsblogs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasamarsblogs.api.Api
import com.example.nasamarsblogs.data.Blog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BlogsViewModel @Inject constructor(private val api: Api) : ViewModel() {

    private val _isBlogsLoading = MutableLiveData<Boolean>()
    val isBlogsLoading: LiveData<Boolean>
        get() = _isBlogsLoading

    private val _loadingError = MutableLiveData<Boolean>()
    val loadingError: LiveData<Boolean>
        get() = _loadingError

    private val _blogs = MutableLiveData<List<Blog>>()
    val blogs: LiveData<List<Blog>>
        get() = _blogs

    fun getBlogs() {
        viewModelScope.launch {
            //clear old list
            _blogs.value = listOf()

            _loadingError.value = false
            _isBlogsLoading.value = true

            val response = try {
                loadBlogs()
            } catch (e: Exception) {
                onError()
                return@launch
            }

            _isBlogsLoading.value = false
            _blogs.value = response.items
        }
    }

    private fun onError() {
        _isBlogsLoading.value = false
        _loadingError.value = true
    }

    private suspend fun loadBlogs() =
        withContext(Dispatchers.IO) {
            api.loadMarsBlogs()
        }

}