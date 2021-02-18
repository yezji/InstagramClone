package com.green.instagramclone.src.register.terms

import android.content.Intent
import android.os.Bundle
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterTermsBinding
import com.green.instagramclone.src.register.usenickname.RegisterConfirmNicknameActivity

class RegisterTermsActivity : BaseActivity<ActivityRegisterTermsBinding>(ActivityRegisterTermsBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding.cbAgreeAll.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbAgree1.isChecked = true
                binding.cbAgree2.isChecked = true
                binding.cbAgree3.isChecked = true
                setBtnStatus(true)
            }
            else {
                binding.cbAgree1.isChecked = false
                binding.cbAgree2.isChecked = false
                binding.cbAgree3.isChecked = false
                setBtnStatus(false)
            }
        }
        binding.cbAgree1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (binding.cbAgree1.isChecked && binding.cbAgree2.isChecked && binding.cbAgree3.isChecked) {
                    binding.cbAgreeAll.isChecked = true
                    setBtnStatus(true)
                }
            }
            else {
                if (!(binding.cbAgree1.isChecked && binding.cbAgree2.isChecked && binding.cbAgree3.isChecked)) {
                    binding.cbAgreeAll.isChecked = false
                    setBtnStatus(false)
                }
            }
        }
        binding.cbAgree2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (binding.cbAgree1.isChecked && binding.cbAgree2.isChecked && binding.cbAgree3.isChecked) {
                    binding.cbAgreeAll.isChecked = true
                    setBtnStatus(true)
                }
            }
            else {
                if (!(binding.cbAgree1.isChecked && binding.cbAgree2.isChecked && binding.cbAgree3.isChecked)) {
                    binding.cbAgreeAll.isChecked = false
                    setBtnStatus(false)
                }
            }
        }
        binding.cbAgree3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (binding.cbAgree1.isChecked && binding.cbAgree2.isChecked && binding.cbAgree3.isChecked) {
                    binding.cbAgreeAll.isChecked = true
                    setBtnStatus(true)
                }
            }
            else {
                if (!(binding.cbAgree1.isChecked && binding.cbAgree2.isChecked && binding.cbAgree3.isChecked)) {
                    binding.cbAgreeAll.isChecked = false
                    setBtnStatus(false)
                }
            }
        }

        binding.btnAgree.setOnClickListener {
            if (binding.btnAgree.isEnabled) {
                val intentReceive = intent
                val userEmail = intentReceive.extras!!.getString("userEmail").toString()
                val userName = intentReceive.extras!!.getString("userName").toString()
                val userPassword = intentReceive.extras!!.getString("userPassword").toString()
                val userBirth = intentReceive.extras!!.getString("userBirth").toString()
                val profileCreateDate = intentReceive.extras!!.getString("profileCreateDate").toString()

                val intentSend = Intent(this, RegisterConfirmNicknameActivity::class.java)
                intentSend.putExtra("userEmail", userEmail)
                intentSend.putExtra("userName", userName)
                intentSend.putExtra("userPassword", userPassword)
                intentSend.putExtra("userBirth", userBirth)
                intentSend.putExtra("profileCreateDate", profileCreateDate)

                startActivity(intentSend)
            }

        }
    }

    private fun setBtnStatus(isEnable: Boolean) {
        if (isEnable) {
            binding.btnAgree.isEnabled = true
            binding.btnAgree.setTextColor(getColor(R.color.white_active))
        }
        else {
            binding.btnAgree.isEnabled = false
            binding.btnAgree.setTextColor(getColor(R.color.white_deactive))
        }
    }

}