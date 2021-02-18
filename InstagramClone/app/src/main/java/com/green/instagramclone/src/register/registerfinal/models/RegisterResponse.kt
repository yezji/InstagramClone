package com.green.instagramclone.src.register.registerfinal.models

import com.google.gson.annotations.SerializedName
import com.green.instagramclone.config.BaseResponse

data class RegisterResponse(
    @SerializedName("jwt") val jwt: String
) : BaseResponse()
