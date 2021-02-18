package com.green.instagramclone.src.register.createid.emailcheck

import com.green.instagramclone.config.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EmailCheckRetrofitInterface {
    @GET("/signup-emails/{userEmails}")
    fun getEmailCheck(@Path("userEmails") userEmail : String) : Call<BaseResponse>
}