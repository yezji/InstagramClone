package com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed

import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed.models.PostCreateFeedRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateFeedRetrofitInterface {
    @POST("/feeds")
    fun postCreateFeed(
        @Body params: PostCreateFeedRequest
    ) : Call<BaseResponse>
}
