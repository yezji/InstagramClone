package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.src.main.home.readfeed.models.AllUserFeedsResponse
import com.green.instagramclone.src.main.home.readfeed.models.FeedLikedUsersResponse
import com.green.instagramclone.src.main.home.readfeed.models.PostCreateLikeFeedRequest
import retrofit2.Call
import retrofit2.http.*

interface ReadFeedRetrofitInterface {
    @GET("/feeds")
    fun getReadFeed() : Call<AllUserFeedsResponse>

    @GET("/feeds/{feedIdx}/likes")
    fun getReadFeedLikedUsers(
        @Path("feedIdx") feedIdx: Int
    ) : Call<FeedLikedUsersResponse>

    @POST("/feeds/{feedIdx}/likes")
    fun postCreateLikeFeed(
        @Path("feedIdx") feedIdx: Int,
        @Body params: PostCreateLikeFeedRequest
    ) : Call<BaseResponse>

    @PATCH("/feeds/{feedIdx}/likes")
    fun patchUpdateLikeFeed(
        @Path("feedIdx") feedIdx: Int,
        @Body userNickNameIdx: Int
    ) : Call<BaseResponse>

}