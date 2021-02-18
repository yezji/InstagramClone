package com.green.instagramclone.src.register.createid

import android.os.Bundle
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterIdBinding

class RegisterIdActivity : BaseActivity<ActivityRegisterIdBinding>(ActivityRegisterIdBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        var viewPager = binding.vp
        viewPager.adapter = RegisterIdAdapter(supportFragmentManager)
        viewPager.currentItem = 0

        // 탭레이아웃에 뷰페이저 연결
        var tabLayout = binding.tabs
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.getTabAt(0)!!.text = "전화번호"
        tabLayout.getTabAt(1)!!.text = "이메일"


        binding.llBtnLoginAccount.setOnClickListener{
            finish()
        }
    }
}