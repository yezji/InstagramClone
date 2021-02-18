package com.green.instagramclone.src.register.usenickname

import android.content.Intent
import android.os.Bundle
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterConfirmNicknameBinding
import com.green.instagramclone.src.register.editnickname.RegisterEditNicknameActivity
import com.green.instagramclone.src.register.scope.RegisterOpenScopeActivity

class RegisterConfirmNicknameActivity : BaseActivity<ActivityRegisterConfirmNicknameBinding>(ActivityRegisterConfirmNicknameBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentReceive = intent
        val userEmail = intentReceive.extras!!.getString("userEmail")
        val userName = intentReceive.extras!!.getString("userName")
        val userPassword = intentReceive.extras!!.getString("userPassword")
        val userBirth = intentReceive.extras!!.getString("userBirth")
        val profileCreateDate = intentReceive.extras!!.getString("profileCreateDate")

        binding.tvNickname.text = userName

        binding.btnNext.setOnClickListener {
            val userNickName = userName

            val intentSend = Intent(this, RegisterOpenScopeActivity::class.java)
            intentSend.putExtra("userEmail", userEmail.toString())
            intentSend.putExtra("userName", userName.toString())
            intentSend.putExtra("userPassword", userPassword.toString())
            intentSend.putExtra("userBirth", userBirth.toString())
            intentSend.putExtra("profileCreateDate", profileCreateDate.toString())
            intentSend.putExtra("userNickName", userNickName.toString())

            startActivity(intentSend)
        }

        binding.btnEditNickname.setOnClickListener {
            val intentSend = Intent(this, RegisterEditNicknameActivity::class.java)
            intentSend.putExtra("userEmail", userEmail.toString())
            intentSend.putExtra("userName", userName.toString())
            intentSend.putExtra("userPassword", userPassword.toString())
            intentSend.putExtra("userBirth", userBirth.toString())
            intentSend.putExtra("profileCreateDate", profileCreateDate.toString())

            startActivity(intentSend)
        }
    }
}