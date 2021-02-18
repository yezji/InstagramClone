package com.green.instagramclone.src.login.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class PostLoginRequest(
    @SerializedName("userEmail") val userEmail: String,
    @SerializedName("userPassword") val userPassword: String,
)
