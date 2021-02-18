package com.green.instagramclone.src.register.editnickname

import com.green.instagramclone.config.BaseResponse

interface NicknameCheckView {
    fun onGetNicknameCheckSuccess(response: BaseResponse)
    fun onGetNicknameCheckFailure(message: String)
}