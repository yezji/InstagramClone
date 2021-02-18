package com.green.instagramclone.src.login

import com.green.instagramclone.src.login.models.LoginResponse
import com.green.instagramclone.src.login.models.PostLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/login")
    fun postLogin(
        @Body params: PostLoginRequest
    ) : Call<LoginResponse>
}