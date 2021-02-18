package com.green.instagramclone.src.register.registerfinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.green.instagramclone.config.ApplicationClass
import com.green.instagramclone.config.ApplicationClass.Companion.USER_NICKNAME_IDX
import com.green.instagramclone.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.green.instagramclone.config.ApplicationClass.Companion.sSharedPreferences
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterProfileBinding
import com.green.instagramclone.src.login.LoginService
import com.green.instagramclone.src.login.LoginView
import com.green.instagramclone.src.login.models.LoginResponse
import com.green.instagramclone.src.login.models.PostLoginRequest
import com.green.instagramclone.src.main.MainActivity
import com.green.instagramclone.src.register.registerfinal.models.PostRegisterRequest
import com.green.instagramclone.src.register.registerfinal.models.RegisterResponse
import java.text.SimpleDateFormat

class RegisterFinalActivity : BaseActivity<ActivityRegisterProfileBinding>(
    ActivityRegisterProfileBinding::inflate),
    RegisterView, LoginView {
    private val editor = sSharedPreferences.edit()

    private lateinit var intentReceive: Intent
    private lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var userNickName: String
    private lateinit var userName: String
    private lateinit var userBirth: String
    private lateinit var profileCreateDate: String
    private lateinit var userDisclosureScope: String
    private lateinit var userProfilePicture: String

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intentReceive = intent
        userEmail = intentReceive.extras!!.getString("userEmail").toString()
        userPassword = intentReceive.extras!!.getString("userPassword").toString()
        userNickName = intentReceive.extras!!.getString("userNickName").toString()
        userName = intentReceive.extras!!.getString("userName").toString()
        userBirth = intentReceive.extras!!.getString("userBirth").toString()
        profileCreateDate = intentReceive.extras!!.getString("profileCreateDate").toString()
        userDisclosureScope = intentReceive.extras!!.getString("userDisclosureScope").toString()
        userProfilePicture = intentReceive.extras!!.getString("userProfilePicture").toString()
        val strBirth = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(userBirth)
        val strCreated = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(profileCreateDate)

        val postRequest = PostRegisterRequest(
            userEmail = userEmail,
            userNickName = userNickName,
            userPassword = userPassword,
            userName = userName,
            userBirth = strBirth!!,
            profileCreateDate = strCreated!!,
            userDisclosureScope = userDisclosureScope,
            userProfilePicture = userProfilePicture
        )

        RegisterService(this).tryPostRegister(postRequest)

    }


    override fun onPostRegisterSuccess(response: RegisterResponse) {
        if (response.isSuccess) {
            // for get jwt
            val postRequest = PostLoginRequest(
                userEmail = userEmail,
                userPassword = userPassword
            )
            LoginService(this).tryPostLogin(postRequest)
        }
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostRegisterFailure(message: String) {
        showCustomToast("회원가입 오류 : $message")
    }

    override fun onPostLoginSuccess(response: LoginResponse) {
        if (response.isSuccess) {
            // save jwt
            editor.putString(X_ACCESS_TOKEN, response.jwt)
            editor.putInt(USER_NICKNAME_IDX, response.userNickNameIdx)
            editor.commit()
            editor.apply()

            val intentSend = Intent(this, MainActivity::class.java)
            intentSend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intentSend)
        }
        else {
            // TODO : 다이얼로그로 response.message 띄우기
        }
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostLoginFailure(message: String) {
        showCustomToast("로그인 오류 : $message")
    }
}