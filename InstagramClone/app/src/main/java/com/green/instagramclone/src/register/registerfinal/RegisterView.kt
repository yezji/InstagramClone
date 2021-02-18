package com.green.instagramclone.src.register.registerfinal

import com.green.instagramclone.src.register.registerfinal.models.RegisterResponse

interface RegisterView {
    fun onPostRegisterSuccess(response: RegisterResponse)
    fun onPostRegisterFailure(message: String)
}