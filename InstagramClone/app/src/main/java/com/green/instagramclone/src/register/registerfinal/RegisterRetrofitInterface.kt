package com.green.instagramclone.src.register.registerfinal

import com.green.instagramclone.src.register.registerfinal.models.PostRegisterRequest
import com.green.instagramclone.src.register.registerfinal.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterRetrofitInterface {
    @POST("/signup")
    fun postRegister(
        @Body params: PostRegisterRequest
    ): Call<RegisterResponse>
}