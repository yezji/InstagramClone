package com.green.instagramclone.src.register.addprofile

import android.content.Intent
import android.os.Bundle
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterProfileBinding
import com.green.instagramclone.src.register.registerfinal.RegisterFinalActivity

class RegisterProfileActivity : BaseActivity<ActivityRegisterProfileBinding>(ActivityRegisterProfileBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentReceive = intent
        val userEmail = intentReceive.extras!!.getString("userEmail")
        val userPassword = intentReceive.extras!!.getString("userPassword")
        val userNickName = intentReceive.extras!!.getString("userNickName")
        val userName = intentReceive.extras!!.getString("userName")
        val userBirth = intentReceive.extras!!.getString("userBirth")
        val profileCreateDate = intentReceive.extras!!.getString("profileCreateDate")
        val userDisclosureScope = intentReceive.extras!!.getString("userDisclosureScope")
        var userProfilePicture = ""

        val intentSend = Intent(this, RegisterFinalActivity::class.java)
        intentSend.putExtra("userEmail", userEmail.toString())
        intentSend.putExtra("userPassword", userPassword.toString())
        intentSend.putExtra("userNickName", userNickName.toString())
        intentSend.putExtra("userName", userName.toString())
        intentSend.putExtra("userBirth", userBirth.toString())
        intentSend.putExtra("profileCreateDate", profileCreateDate.toString())
        intentSend.putExtra("userDisclosureScope", userDisclosureScope.toString())


        binding.btnNext.setOnClickListener {
            // TODO: 사진 추가 다이얼로그

            // TODO: 사진 저장소에 업로드 후 생긴 URL 넣어주기
            userProfilePicture = "change"

            intentSend.putExtra("userProfilePicture", userProfilePicture)
            startActivity(intentSend)
        }

        binding.btnSkip.setOnClickListener {
            intentSend.putExtra("userProfilePicture", userProfilePicture)
            startActivity(intentSend)
        }

    }

}