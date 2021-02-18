package com.green.instagramclone.src.register.authemailcode

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityAuthEmailBinding
import com.green.instagramclone.src.register.createnickname.RegisterNicknameActivity

@Suppress("NAME_SHADOWING")
class AuthEmailActivity : BaseActivity<ActivityAuthEmailBinding>(ActivityAuthEmailBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentReceive = intent
        val userEmail = intentReceive.extras!!.getString("userEmail")
        binding.tvAuthemailEmail.text = userEmail

        val etCode = binding.etCode
        etCode.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    setBtnStatus(s.length == 6)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    setBtnStatus(s.length == 6)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    setBtnStatus(s.length == 6)
                }
            }
        })
        binding.btnRegisterNext.setOnClickListener {
            val intentSend = Intent(this, RegisterNicknameActivity::class.java)
            intentSend.putExtra("userEmail", userEmail.toString())
            startActivity(intentSend)
        }
    }
    fun setBtnStatus(isEnable: Boolean) {
        val btn = binding.btnRegisterNext
        if (isEnable) {
            btn.isEnabled = true
            btn.setTextColor(getColor(R.color.white_active))
        }
        else {
            btn.isEnabled = false
            btn.setTextColor(getColor(R.color.white_deactive))
        }
    }
}