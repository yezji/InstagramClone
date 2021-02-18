package com.green.instagramclone.src.register.createid.emailcheck

import com.green.instagramclone.config.BaseResponse

interface EmailCheckView {
    fun onGetEmailCheckSuccess(response: BaseResponse)
    fun onGetEmailCheckFailure(message: String)
}