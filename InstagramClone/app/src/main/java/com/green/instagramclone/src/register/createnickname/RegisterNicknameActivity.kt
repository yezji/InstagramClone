package com.green.instagramclone.src.register.createnickname

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterNicknameBinding
import com.green.instagramclone.src.register.birthday.RegisterBirthdayActivity
import java.util.regex.Pattern

class RegisterNicknameActivity : BaseActivity<ActivityRegisterNicknameBinding>(ActivityRegisterNicknameBinding::inflate) {
    var patternNickname = Pattern.compile("^[A-Za-z0-9가-힇_.]*$")
    var isNicknameEnable = false
    var isPasswordEnable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userName = binding.etName
        var etPassword = binding.etPassword

        userName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (patternNickname.matcher(s).matches()) {
                            isNicknameEnable = true
                            binding.textfieldNickname.isErrorEnabled = false
                            if (isNicknameEnable && isPasswordEnable) isEnable = true
                        }
                        else {
                            isNicknameEnable = false
                            binding.textfieldNickname.isErrorEnabled = true
                            binding.textfieldNickname.error = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다."
                        }
                    }
                    else {
                        isNicknameEnable = false
                        binding.textfieldNickname.isErrorEnabled = false
                    }
                    setBtnStatus(isEnable)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (patternNickname.matcher(s).matches()) {
                            isNicknameEnable = true
                            binding.textfieldNickname.isErrorEnabled = false
                            if (isNicknameEnable && isPasswordEnable) isEnable = true
                        }
                        else {
                            isNicknameEnable = false
                            binding.textfieldNickname.isErrorEnabled = true
                            binding.textfieldNickname.error = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다."
                        }
                    }
                    else {
                        isNicknameEnable = false
                        binding.textfieldNickname.isErrorEnabled = false
                    }
                    setBtnStatus(isEnable)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (patternNickname.matcher(s).matches()) {
                            isNicknameEnable = true
                            binding.textfieldNickname.isErrorEnabled = false
                            if (isNicknameEnable && isPasswordEnable) isEnable = true
                        }
                        else {
                            isNicknameEnable = false
                            binding.textfieldNickname.isErrorEnabled = true
                            binding.textfieldNickname.error = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다."
                        }
                    }
                    else {
                        isNicknameEnable = false
                        binding.textfieldNickname.isErrorEnabled = false
                    }
                    setBtnStatus(isEnable)
                }
            }
        })

        etPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (s.length >= 6) {
                            isPasswordEnable = true
                            binding.textfieldPassword.isErrorEnabled = false
                            if (isNicknameEnable && isPasswordEnable) isEnable = true
                        }
                        else {
                            isPasswordEnable = false
                            binding.textfieldPassword.isErrorEnabled = true
                            binding.textfieldPassword.error = "비밀번호는 6자 이상이어야 합니다."
                        }
                    }
                    else {
                        isPasswordEnable = false
                        binding.textfieldPassword.isErrorEnabled = false
                    }
                    setBtnStatus(isEnable)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (s.length >= 6) {
                            isPasswordEnable = true
                            binding.textfieldPassword.isErrorEnabled = false
                            if (isNicknameEnable && isPasswordEnable) isEnable = true
                        }
                        else {
                            isPasswordEnable = false
                            binding.textfieldPassword.isErrorEnabled = true
                            binding.textfieldPassword.error = "비밀번호는 6자 이상이어야 합니다."
                        }
                    }
                    else {
                        isPasswordEnable = false
                        binding.textfieldPassword.isErrorEnabled = false
                    }
                    setBtnStatus(isEnable)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (s.length >= 6) {
                            isPasswordEnable = true
                            binding.textfieldPassword.isErrorEnabled = false
                            if (isNicknameEnable && isPasswordEnable) isEnable = true
                        }
                        else {
                            isPasswordEnable = false
                            binding.textfieldPassword.isErrorEnabled = true
                            binding.textfieldPassword.error = "비밀번호는 6자 이상이어야 합니다."
                        }
                    }
                    else {
                        isPasswordEnable = false
                        binding.textfieldPassword.isErrorEnabled = false
                    }
                    setBtnStatus(isEnable)
                }
            }
        })

        binding.btnSyncOff.setOnClickListener {
            if (binding.btnSyncOff.isEnabled) {
                // TODO: 중복된 닉네임 가지고 있는지 검사
//            etNickname.text
                // TODO: 중복이라면? 다이얼로그 띄우기
                // TODO: 중복이 아니라면? 다음 단계로
                var intentReceive = intent
                var userEmail = intentReceive.extras!!.getString("userEmail")

                var intentSend = Intent(this, RegisterBirthdayActivity::class.java)
                intentSend.putExtra("userEmail", userEmail.toString())
                intentSend.putExtra("userName", userName.text.toString())
                intentSend.putExtra("userPassword", etPassword.text.toString())
                startActivity(intentSend)
            }
        }

    }

    fun setBtnStatus(isEnable: Boolean) {
        val btnSyncOn = binding.btnSyncOn
        val btnSyncOff = binding.btnSyncOff
        if (isEnable) {
            btnSyncOn.isEnabled = true
            btnSyncOn.setTextColor(getColor(R.color.white_active))
            btnSyncOff.isEnabled = true
            btnSyncOff.setTextColor(getColor(R.color.blue_active))
        }
        else {
            btnSyncOn.isEnabled = false
            btnSyncOn.setTextColor(getColor(R.color.white_deactive))
            btnSyncOff.isEnabled = false
            btnSyncOff.setTextColor(getColor(R.color.blue_deactive))
        }
    }
}