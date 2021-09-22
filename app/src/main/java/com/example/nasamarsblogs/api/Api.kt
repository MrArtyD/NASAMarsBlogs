package com.example.nasamarsblogs.api

import com.example.nasamarsblogs.api.response.ResponseBlogs
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object{
        const val BASE_URL = "https://api.rss2json.com/v1/"
    }

    @GET("api.json")
    suspend fun loadMarsBlogs(
        @Query("rss_url") rssUrl : String = "https://mars.nasa.gov/rss/blogs.cfm"
    ) : ResponseBlogs
}