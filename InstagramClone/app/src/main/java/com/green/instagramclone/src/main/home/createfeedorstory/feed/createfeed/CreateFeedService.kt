package com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed

import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed.models.PostCreateFeedRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateFeedService(val view: CreateFeedView) {
    fun tryPostCreateFeed(postCreateFeedRequest: PostCreateFeedRequest) {
        val createFeedRetrofitInterface = ApplicationClass.sRetrofit.create(CreateFeedRetrofitInterface::class.java)
        createFeedRetrofitInterface.postCreateFeed(postCreateFeedRequest).enqueue(object :
        Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                view.onPostCreateFeedSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostCreateFeedFailure(t.message ?: "통신 오류")
            }
        })
    }
}