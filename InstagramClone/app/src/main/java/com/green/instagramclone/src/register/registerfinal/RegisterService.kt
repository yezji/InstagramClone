package com.green.instagramclone.src.register.registerfinal

import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.src.register.registerfinal.models.PostRegisterRequest
import com.green.instagramclone.src.register.registerfinal.models.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterService(val view: RegisterView) {
    fun tryPostRegister(postRegisterRequest: PostRegisterRequest){
        val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)
        registerRetrofitInterface.postRegister(postRegisterRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                view.onPostRegisterSuccess(response.body() as RegisterResponse)
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                view.onPostRegisterFailure(t.message ?: "통신 오류")
            }
        })
    }

}