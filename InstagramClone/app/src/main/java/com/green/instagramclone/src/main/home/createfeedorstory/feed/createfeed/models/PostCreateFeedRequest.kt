package com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed.models

import com.google.gson.annotations.SerializedName

data class PostCreateFeedRequest(
    @SerializedName("userNickNameIdx") val userNickNameIdx: Int,
    @SerializedName("caption") val caption: String,
    @SerializedName("feedCreateDate") val feedCreateDate: String,
    @SerializedName("feedUpdateDate") val feedUpdateDate: String,
    @SerializedName("mediaIdx") val mediaIdx: Int,
    @SerializedName("mediaList") val mediaList: MutableList<MediaURL>
)

data class MediaURL(
    @SerializedName("mediaURL") val mediaURL: String
)
