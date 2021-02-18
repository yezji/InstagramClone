package com.green.instagramclone.src.login

import com.green.instagramclone.src.login.models.LoginResponse

interface LoginView {
    fun onPostLoginSuccess(response: LoginResponse)
    fun onPostLoginFailure(message: String)
}