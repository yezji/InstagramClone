package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.src.main.home.readfeed.models.UserFeedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadFeedService(val view: ReadFeedView) {
    fun tryGetFeed() {
        val readFeedRetrofitInterface = ApplicationClass.sRetrofit.create(
            ReadFeedRetrofitInterface::class.java)
        readFeedRetrofitInterface.getReadFeed().enqueue(object : Callback<UserFeedResponse> {
            override fun onResponse(
                call: Call<UserFeedResponse>,
                response: Response<UserFeedResponse>
            ) {
                view.onGetFeedSuccess(response.body() as UserFeedResponse)
            }

            override fun onFailure(call: Call<UserFeedResponse>, t: Throwable) {
                view.onGetFeedFailure(t.message ?: "통신 오류")
            }
        })
    }
}