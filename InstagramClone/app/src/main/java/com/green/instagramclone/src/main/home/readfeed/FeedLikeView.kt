package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.src.main.home.readfeed.models.FeedLikedUsersResponse

interface FeedLikeView {
    fun onGetFeedLikedUsersSuccess(response: FeedLikedUsersResponse)
    fun onGetFeedLikedUsersFailure(message: String)

    fun onPostFeedLikeSuccess(response: BaseResponse)
    fun onPostFeedLikeFailure(message: String)

    fun onPatchFeedLikeSuccess(response: BaseResponse)
    fun onPatchFeedLikeFailure(message: String)
}