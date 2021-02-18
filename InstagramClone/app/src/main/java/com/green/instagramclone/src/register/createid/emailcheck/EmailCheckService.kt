package com.green.instagramclone.src.register.createid.emailcheck

import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.config.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailCheckService (val view: EmailCheckView) {
    fun tryGetEmailCheck(userEmail: String) {
        val emailCheckRetrofitInterface = ApplicationClass.sRetrofit.create(
            EmailCheckRetrofitInterface::class.java)
        emailCheckRetrofitInterface.getEmailCheck(userEmail).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                view.onGetEmailCheckSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onGetEmailCheckFailure(t.message ?: "통신 오류")
            }
        })
    }
}