package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.src.main.home.readfeed.models.UserFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReadFeedRetrofitInterface {
    @GET("/feeds")
    fun getReadFeed() : Call<UserFeedResponse>
}