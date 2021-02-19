package com.green.instagramclone.src.main.home.readfeed.models

import com.google.gson.annotations.SerializedName

data class PostCreateLikeFeedRequest(
    @SerializedName("userNickNameIdx") val userNickNameIdx: Int
)
