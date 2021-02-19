package com.green.instagramclone.src.main.home.readfeed.models

import com.google.gson.annotations.SerializedName
import com.green.instagramclone.config.BaseResponse

data class AllUserFeedsResponse(
    @SerializedName("data") val data: MutableList<Feed>
) : BaseResponse()

data class Feed(
    @SerializedName("feedIdx") val feedIdx: Int,
    @SerializedName("userNickNameIdx") val userNickNameIdx: Int,
    @SerializedName("userNickName") val userNickName : String,
    @SerializedName("userProfilePicture") val userProfilePicture: String?,
    @SerializedName("caption") val caption: String,
    @SerializedName("likeCount") val likeCount: Int?,
    @SerializedName("commentCount") val commentCount: Int?,

    @SerializedName("mediaIdx") val mediaIdx: Int,
    @SerializedName("mediaURL") val mediaURL: String,

    @SerializedName("firstCommentIdx") val firstCommentIdx: Int?,
    @SerializedName("firstCommentText") val firstCommentText: String?,
    @SerializedName("firstUserNickNameIdx") val firstUserNickNameIdx: Int?,
    @SerializedName("firstUserNickName") val firstUserNickName: String?,
    @SerializedName("feedCreateDate") val feedCreateDate: String
)