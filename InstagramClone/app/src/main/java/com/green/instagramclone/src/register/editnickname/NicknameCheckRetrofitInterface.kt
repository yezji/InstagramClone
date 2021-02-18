package com.green.instagramclone.src.register.editnickname

import com.green.instagramclone.config.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NicknameCheckRetrofitInterface {
    @GET("/signup-nicknames/{userNickNames}")
    fun getNicknameCheck(@Path("userNickNames") userNickName : String) : Call<BaseResponse>
}