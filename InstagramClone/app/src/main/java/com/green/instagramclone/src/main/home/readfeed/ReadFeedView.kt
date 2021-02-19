package com.green.instagramclone.src.main.home.readfeed

import com.green.instagramclone.src.main.home.readfeed.models.AllUserFeedsResponse

interface ReadFeedView {
    fun onGetFeedSuccess(response: AllUserFeedsResponse)
    fun onGetFeedFailure(message: String)
}

