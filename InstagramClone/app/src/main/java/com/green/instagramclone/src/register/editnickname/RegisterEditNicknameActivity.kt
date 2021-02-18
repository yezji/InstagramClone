package com.green.instagramclone.src.register.editnickname

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.databinding.ActivityRegisterEditNicknameBinding
import com.green.instagramclone.src.register.scope.RegisterOpenScopeActivity
import java.util.regex.Pattern

class RegisterEditNicknameActivity : BaseActivity<ActivityRegisterEditNicknameBinding>(ActivityRegisterEditNicknameBinding::inflate),
NicknameCheckView {
    val patternNickname: Pattern = Pattern.compile("^[A-Za-z0-9_.]*$")
    private lateinit var userNickName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userNickName = binding.etNickname

        userNickName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        if (patternNickname.matcher(s).matches()) {
                            setBtnStatus(true)
                            binding.textfieldNickname.isErrorEnabled = false
                        }
                        else {
                            setBtnStatus(false)
                            binding.textfieldNickname.isErrorEnabled = true
                            binding.textfieldNickname.error = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다."
                        }
                    }
                    else {
                        setBtnStatus(false)
                        binding.textfieldNickname.isErrorEnabled = false
                    }
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        if (patternNickname.matcher(s).matches()) {
                            setBtnStatus(true)
                            binding.textfieldNickname.isErrorEnabled = false
                        }
                        else {
                            setBtnStatus(false)
                            binding.textfieldNickname.isErrorEnabled = true
                            binding.textfieldNickname.error = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다."
                        }
                    }
                    else {
                        setBtnStatus(false)
                        binding.textfieldNickname.isErrorEnabled = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (!s.isEmpty()) {
                        if (patternNickname.matcher(s).matches()) {
                            setBtnStatus(true)
                            binding.textfieldNickname.isErrorEnabled = false
                        }
                        else {
                            setBtnStatus(false)
                            binding.textfieldNickname.isErrorEnabled = true
                            binding.textfieldNickname.error = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다."
                        }
                    }
                    else {
                        setBtnStatus(false)
                        binding.textfieldNickname.isErrorEnabled = false
                    }
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.isEnabled) {

                NicknameCheckService(this).tryGetNicknameCheck(userNickName = binding.etNickname.text.toString())

            }
        }
    }


    fun setBtnStatus(isEnable: Boolean) {
        if (isEnable) {
            binding.btnNext.isEnabled = true
            binding.btnNext.setTextColor(getColor(R.color.white_active))
        }
        else {
            binding.btnNext.isEnabled = false
            binding.btnNext.setTextColor(getColor(R.color.white_deactive))
        }
    }

    override fun onGetNicknameCheckSuccess(response: BaseResponse) {
        if (response.isSuccess) { // 성공
            val intentReceive = intent
            val userEmail = intentReceive.extras!!.getString("userEmail").toString()
            val userName = intentReceive.extras!!.getString("userName").toString()
            val userPassword = intentReceive.extras!!.getString("userPassword").toString()
            val userBirth = intentReceive.extras!!.getString("userBirth").toString()
            val profileCreateDate = intentReceive.extras!!.getString("profileCreateDate").toString()

            val intentSend = Intent(this, RegisterOpenScopeActivity::class.java)
            intentSend.putExtra("userEmail", userEmail)
            intentSend.putExtra("userName", userName)
            intentSend.putExtra("userPassword", userPassword)
            intentSend.putExtra("userBirth", userBirth)
            intentSend.putExtra("profileCreateDate", profileCreateDate)
            intentSend.putExtra("userNickName", userNickName.text.toString())

            startActivity(intentSend)
        }
        else {
            setBtnStatus(false)
            binding.textfieldNickname.isErrorEnabled = true
            when (response.code) {
                2061 -> {
                    binding.textfieldNickname.error = "사용자 이름은 최대 20자리를 입력해주세요."
                }
                3062 -> {
                    binding.textfieldNickname.error = "이 사용자 이름은 이미 다른 사람이 사용하고 있습니다."
                }
            }
        }
        response.message?.let { showCustomToast(it) }
    }

    override fun onGetNicknameCheckFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}