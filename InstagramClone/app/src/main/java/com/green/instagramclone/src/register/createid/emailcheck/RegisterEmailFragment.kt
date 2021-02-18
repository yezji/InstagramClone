package com.green.instagramclone.src.register.createid.emailcheck

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.databinding.FragmentRegisterEmailBinding
import com.green.instagramclone.src.register.authemailcode.AuthEmailActivity
import java.util.regex.Pattern

class RegisterEmailFragment : BaseFragment<FragmentRegisterEmailBinding>(FragmentRegisterEmailBinding::bind, R.layout.fragment_register_email),
EmailCheckView {
    val patternEmail: Pattern = android.util.Patterns.EMAIL_ADDRESS
    private lateinit var etEmail: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmail = binding.etEmail

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (patternEmail.matcher(s).matches()) isEnable = true
                    }
                    setBtnStatus(isEnable)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (patternEmail.matcher(s).matches()) isEnable = true
                    }
                    setBtnStatus(isEnable)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    var isEnable = false
                    if (!s.isEmpty()) {
                        if (patternEmail.matcher(s).matches()) isEnable = true
                    }
                    setBtnStatus(isEnable)
                }
            }
        })
        
        binding.btnRegisterNext.setOnClickListener {
            if (binding.btnRegisterNext.isEnabled) {
                EmailCheckService(this).tryGetEmailCheck(etEmail.text.toString())
            }
        }

    }
    fun setBtnStatus(isEnable: Boolean) {
        val btn = binding.btnRegisterNext
        if (isEnable) {
            btn.isEnabled = true
            btn.setTextColor(context!!.getColor(R.color.white_active))
        }
        else {
            btn.isEnabled = false
            btn.setTextColor(context!!.getColor(R.color.white_deactive))
        }
    }

    override fun onGetEmailCheckSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            val intent = Intent(context, AuthEmailActivity::class.java)
            intent.putExtra("userEmail", etEmail.text.toString())
            startActivity(intent)
        }
        else {
            // TODO : 다이얼로그로 response.message 띄우기
        }
        response.message?.let { showCustomToast(it) }
    }

    override fun onGetEmailCheckFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}