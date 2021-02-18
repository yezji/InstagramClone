package com.green.instagramclone.src.register.scope

import android.content.Intent
import android.os.Bundle
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterOpenScopeBinding
import com.green.instagramclone.src.register.addprofile.RegisterProfileActivity

@Suppress("NAME_SHADOWING")
class RegisterOpenScopeActivity : BaseActivity<ActivityRegisterOpenScopeBinding>(ActivityRegisterOpenScopeBinding::inflate) {
    var userDisclosureScope = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rbScopeLock.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.rbScopeUnlock.isChecked = false
                userDisclosureScope = "0"
                true.setBtnStatus()
            }
        }
        binding.rbScopeUnlock.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.rbScopeLock.isChecked = false
                userDisclosureScope = "1"
                true.setBtnStatus()
            }
        }

        binding.btnNext.setOnClickListener {
            if (binding.rbScopeLock.isChecked || binding.rbScopeUnlock.isChecked) {
                val intentReceive = intent
                val userEmail = intentReceive.extras!!.getString("userEmail")
                val userPassword = intentReceive.extras!!.getString("userPassword")
                val userNickName = intentReceive.extras!!.getString("userNickName")
                val userName = intentReceive.extras!!.getString("userName")
                val userBirth = intentReceive.extras!!.getString("userBirth")
                val profileCreateDate = intentReceive.extras!!.getString("profileCreateDate")
                val userDisclosureScope = intentReceive.extras!!.getString("userDisclosureScope")

                val intentSend = Intent(this, RegisterProfileActivity::class.java)
                intentSend.putExtra("userEmail", userEmail)
                intentSend.putExtra("userPassword", userPassword)
                intentSend.putExtra("userNickName", userNickName)
                intentSend.putExtra("userName", userName)
                intentSend.putExtra("userBirth", userBirth)
                intentSend.putExtra("profileCreateDate", profileCreateDate)
                intentSend.putExtra("userDisclosureScope", userDisclosureScope)

                startActivity(intentSend)
            }


        }
    }

    private fun Boolean.setBtnStatus() {
        if (this) {
            binding.btnNext.isEnabled = true
            binding.btnNext.setTextColor(getColor(R.color.white_active))
        }
        else {
            binding.btnNext.isEnabled = false
            binding.btnNext.setTextColor(getColor(R.color.white_deactive))
        }
    }

}