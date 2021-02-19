package com.green.instagramclone.src.main.home.readfeed.models

import com.google.gson.annotations.SerializedName
import com.green.instagramclone.config.BaseResponse

data class FeedLikedUsersResponse(
    @SerializedName("data") val data: MutableList<LikedUsers>
) : BaseResponse()

data class LikedUsers(
    @SerializedName("userNickNameIdx") val userNickNameIdx: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("userNickName") val userNickName: String,
    @SerializedName("userProfilePicture") val userProfilePicture: String?
)