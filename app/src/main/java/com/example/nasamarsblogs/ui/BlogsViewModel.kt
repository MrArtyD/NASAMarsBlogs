package com.example.nasamarsblogs.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.nasamarsblogs.api.Api
import com.example.nasamarsblogs.data.Blog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

const val TAG = "BlogsFragment"

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

    val isBlogsEmpty = _blogs.switchMap { list ->
        _isBlogsLoading.switchMap { isLoading ->
            _loadingError.map { isError ->
                list.isEmpty() && !isLoading && !isError
            }
        }
    }

    fun getBlogs() {
        viewModelScope.launch {
            //clear old list
            _blogs.value = listOf()

            _loadingError.value = false
            _isBlogsLoading.value = true

            val response = try {
                loadBlogs()
            } catch (e: IOException) {
                onError()
                Log.e(TAG, "IOException, something might be wrong with the internet connection.")
                return@launch
            } catch (e: HttpException) {
                onError()
                Log.e(TAG, "HttpException, response is incorrect")
                return@launch
            } catch (e: SocketTimeoutException) {
                onError()
                Log.e(TAG, "SocketTimeoutException, possibly the internet connection is too slow.")
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