package com.green.instagramclone.src.register.birthday

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityRegisterBirthdayBinding
import com.green.instagramclone.src.register.terms.RegisterTermsActivity
import java.text.SimpleDateFormat
import java.util.*

class RegisterBirthdayActivity : BaseActivity<ActivityRegisterBirthdayBinding>(
    ActivityRegisterBirthdayBinding::inflate
) {
    val calendar = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    var strToday: String = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
    var strBirthday = ""

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val strTodayArray = strToday.split("-")
        setString(
            strTodayArray[0].toInt(),
            strTodayArray[1].toInt(),
            strTodayArray[2].toInt(),
            strTodayArray[0].toInt()
        )

        binding.datepicker.maxDate = System.currentTimeMillis()

        binding.datepicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            setString(year, monthOfYear + 1, dayOfMonth, strTodayArray[0].toInt())
        }

        binding.btnBirthdayNext.setOnClickListener {
            if (isUnderAge(strBirthday.substring(0, 4).toInt(), strTodayArray[0].toInt())) {
                // TODO: 다이얼로그로 교체하기
                showCustomToast("실제 생일을 입력하세요.")
            }
            else {
                val date = strBirthday+" 00:00:00"
                val created = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.time)

                val intentReceive = intent
                val userEmail = intentReceive.extras!!.getString("userEmail").toString()
                val userName = intentReceive.extras!!.getString("userName").toString()
                val userPassword = intentReceive.extras!!.getString("userPassword").toString()

                val intentSend = Intent(this, RegisterTermsActivity::class.java)
                intentSend.putExtra("userEmail", userEmail)
                intentSend.putExtra("userName", userName)
                intentSend.putExtra("userPassword", userPassword)

                intentSend.putExtra("userBirth", date)
                intentSend.putExtra("profileCreateDate", created.toString())

                startActivity(intentSend)
            }
        }

    }
    fun isUnderAge(selectYear: Int, nowYear: Int) : Boolean {
        return nowYear-selectYear <= 5
    }
    @SuppressLint("SetTextI18n")
    fun setString(year: Int, month: Int, day: Int, nowYear: Int) {
        var strDate = ""
        var y = year.toString()
        var m = month.toString()
        var d = day.toString()
        if (year<10) y = "0"+y
        if (month<10) m = "0"+m
        if (day<10) d = "0"+d
        strDate += year.toString() + getString(R.string.et_year) + " "
        strDate += month.toString() + getString(R.string.et_month) + " "
        strDate += day.toString() + getString(R.string.et_day)
        binding.etBirthdayFixed.setText(strDate)
        strBirthday = y+"-"+m+"-"+d

        val age = nowYear-year
        if (age <= 5) binding.tvBirthdayAge.setTextColor(Color.RED)
        else binding.tvBirthdayAge.setTextColor(getColor(R.color.et_dialog_gray))
        binding.tvBirthdayAge.text = age.toString() + "세"
    }
}