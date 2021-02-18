package com.green.instagramclone.src.register.registerfinal.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class PostRegisterRequest(
    @SerializedName("userEmail") val userEmail: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("userPassword") val userPassword: String,
    @SerializedName("userBirth") val userBirth: Date,
    @SerializedName("userNickName") val userNickName: String,
    @SerializedName("userDisclosureScope") val userDisclosureScope: String,
    @SerializedName("userProfilePicture") val userProfilePicture: String?,
    @SerializedName("profileCreateDate") val profileCreateDate: Date
)
