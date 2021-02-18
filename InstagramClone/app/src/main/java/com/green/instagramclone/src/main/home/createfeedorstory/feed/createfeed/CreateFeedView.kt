package com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed

import com.green.instagramclone.config.BaseResponse

interface CreateFeedView {
    fun onPostCreateFeedSuccess(response: BaseResponse)
    fun onPostCreateFeedFailure(message: String)
}