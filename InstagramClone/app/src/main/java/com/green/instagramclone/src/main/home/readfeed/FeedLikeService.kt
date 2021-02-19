package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.src.main.home.readfeed.models.FeedLikedUsersResponse
import com.green.instagramclone.src.main.home.readfeed.models.PostCreateLikeFeedRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedLikeService(val view: FeedLikeView) {
    fun tryGetFeedLikedUsers(feedIdx: Int) {
        val readFeedRetrofitInterface = ApplicationClass.sRetrofit.create(
            ReadFeedRetrofitInterface::class.java)
        readFeedRetrofitInterface.getReadFeedLikedUsers(feedIdx).enqueue(object :
            Callback<FeedLikedUsersResponse> {
            override fun onResponse(
                call: Call<FeedLikedUsersResponse>,
                response: Response<FeedLikedUsersResponse>
            ) {
                view.onGetFeedLikedUsersSuccess(response.body() as FeedLikedUsersResponse)
            }

            override fun onFailure(call: Call<FeedLikedUsersResponse>, t: Throwable) {
                view.onGetFeedLikedUsersFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostFeedLike(feedIdx: Int, params: PostCreateLikeFeedRequest) {
        val readFeedRetrofitInterface = ApplicationClass.sRetrofit.create(
            ReadFeedRetrofitInterface::class.java)
        readFeedRetrofitInterface.postCreateLikeFeed(feedIdx, params).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                view.onPostFeedLikeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPostFeedLikeFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchFeedLike(feedIdx: Int, userNickNameIdx: Int) {
        val readFeedRetrofitInterface = ApplicationClass.sRetrofit.create(
            ReadFeedRetrofitInterface::class.java)
        readFeedRetrofitInterface.patchUpdateLikeFeed(feedIdx, userNickNameIdx).enqueue(object :
            Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                view.onPatchFeedLikeSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onPatchFeedLikeFailure(t.message ?: "통신 오류")
            }
        })
    }
}