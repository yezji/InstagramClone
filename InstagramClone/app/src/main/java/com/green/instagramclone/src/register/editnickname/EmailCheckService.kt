package com.green.instagramclone.src.register.editnickname

import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.config.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NicknameCheckService (val view: NicknameCheckView) {
    fun tryGetNicknameCheck(userNickName: String) {
        val nicknameCheckRetrofitInterface = ApplicationClass.sRetrofit.create(
            NicknameCheckRetrofitInterface::class.java)
        nicknameCheckRetrofitInterface.getNicknameCheck(userNickName).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(
                call: Call<BaseResponse>,
                response: Response<BaseResponse>
            ) {
                view.onGetNicknameCheckSuccess(response.body() as BaseResponse)
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                view.onGetNicknameCheckFailure(t.message ?: "통신 오류")
            }
        })
    }
}