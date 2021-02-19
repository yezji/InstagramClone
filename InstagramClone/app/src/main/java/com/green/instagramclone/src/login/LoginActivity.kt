package com.green.instagramclone.src.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.green.instagramclone.R
import com.green.instagramclone.config.ApplicationClass.Companion.USER_NICKNAME_IDX
import com.green.instagramclone.config.ApplicationClass.Companion.USER_PROFILE_PICTURE
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityLoginBinding
import com.green.instagramclone.src.login.models.LoginResponse
import com.green.instagramclone.src.login.models.PostLoginRequest
import com.green.instagramclone.src.register.createid.RegisterIdActivity
import com.green.instagramclone.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.green.instagramclone.config.ApplicationClass.Companion.sSharedPreferences
import com.green.instagramclone.src.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginView {
    private val editor = sSharedPreferences.edit()

//    lateinit var dlg: DialogConfirm
    var isIdAvailable = false
    var isPwAvailable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO : Dialog 적용하기
//        dlg = DialogConfirm(applicationContext)
//        dlg.setDialog(getString(R.string.tv_login_dialog_title), getString(R.string.tv_login_dialog_description))

        binding.etId.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 작성 전
                isIdAvailable = false
                setBtnStatus()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 작성 중
                isIdAvailable = true
                setBtnStatus()
            }
            override fun afterTextChanged(s: Editable?) {
                // 작성 후
                if (s != null && s.isEmpty()) isIdAvailable = false
                setBtnStatus()
            }
        })
        binding.etPw.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 작성 전
                isPwAvailable = false
                setBtnStatus()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 작성 중
                isPwAvailable = true
                setBtnStatus()
            }
            override fun afterTextChanged(s: Editable?) {
                // 작성 후
                if (s != null && s.isEmpty()) isPwAvailable = false
                setBtnStatus()
            }
        })

        binding.btnLoginApp.setOnClickListener {
            val postRequest = PostLoginRequest(
                userEmail = binding.etId.text.toString(),
                userPassword = binding.etPw.text.toString()
            )

            LoginService(this).tryPostLogin(postRequest)
        }

        binding.llBtnMakeAccount.setOnClickListener {
            startActivity(Intent(this, RegisterIdActivity::class.java))
//            finish()
        }
    }

    fun setBtnStatus() {
        val btn = binding.btnLoginApp
        if (isIdAvailable && isPwAvailable) {
            btn.isEnabled = true
            btn.setTextColor(getColor(R.color.white_active))
        }
        else {
            btn.isEnabled = false
            btn.setTextColor(getColor(R.color.white_deactive))
        }
    }

    override fun onPostLoginSuccess(response: LoginResponse) {
        if (response.isSuccess) {
            editor.putString(X_ACCESS_TOKEN, response.jwt)
            editor.putInt(USER_NICKNAME_IDX, response.userNickNameIdx)
            editor.putString(USER_PROFILE_PICTURE, response.userProfilePicture)
            editor.commit()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            // TODO : 다이얼로그로 response.message 띄우기
        }
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostLoginFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}