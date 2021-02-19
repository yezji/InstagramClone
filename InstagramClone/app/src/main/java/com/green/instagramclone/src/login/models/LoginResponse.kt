package com.green.instagramclone.src.login.models

import com.google.gson.annotations.SerializedName
import com.green.instagramclone.config.BaseResponse

data class LoginResponse(
    @SerializedName("jwt") val jwt: String,
    @SerializedName("userNickNameIdx") val userNickNameIdx: Int,
    @SerializedName("userProfilePicture") val userProfilePicture: String?
) : BaseResponse()
