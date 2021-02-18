package com.green.instagramclone.src.register.createid.phonecheck

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentRegisterPhoneBinding

class RegisterPhoneFragment : BaseFragment<FragmentRegisterPhoneBinding>(FragmentRegisterPhoneBinding::bind, R.layout.fragment_register_phone) {
    val patternPhone = android.util.Patterns.PHONE
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var etPhone = binding.etPhone
        etPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        etPhone.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s != null) {
                    setBtnStatus(s.length == 13 && patternPhone.matcher(s).matches())
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    setBtnStatus(s.length == 13 && patternPhone.matcher(s).matches())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    setBtnStatus(s.length == 13 && patternPhone.matcher(s).matches())
                }
            }
        })

        binding.btnRegisterNext.setOnClickListener {
//
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

}