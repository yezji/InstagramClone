package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.src.main.home.readfeed.models.UserFeedResponse

interface ReadFeedView {
    fun onGetFeedSuccess(response: UserFeedResponse)
    fun onGetFeedFailure(message: String)
}