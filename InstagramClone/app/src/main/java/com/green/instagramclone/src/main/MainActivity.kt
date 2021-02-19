package com.green.instagramclone.src.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.green.instagramclone.R
import com.green.instagramclone.R.color
import com.green.instagramclone.config.ApplicationClass.Companion.USER_PROFILE_PICTURE
import com.green.instagramclone.config.ApplicationClass.Companion.homeFragment
import com.green.instagramclone.config.ApplicationClass.Companion.mainActivity
import com.green.instagramclone.config.ApplicationClass.Companion.sSharedPreferences
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.databinding.ActivityMainBinding
import com.green.instagramclone.src.main.home.*

@Suppress("NAME_SHADOWING")
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val tagList = mutableListOf("home", "search", "reels", "shopping", "mypage")
    private val resList = mutableListOf(R.id.menu_home, R.id.menu_search, R.id.menu_reels, R.id.menu_shopping, R.id.menu_mypage)
    lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = this
        homeFragment = HomeFragment()
        bottomNavigationView = binding.bottomNavigation

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, homeFragment).commit()

        binding.bottomNavigation.menu.findItem(resList[0]).isChecked = true
        setBottomNavigationColorMode(whiteMode = true)

        // set my profile at bnv
        Glide.with(this)
            .load(sSharedPreferences.getString(USER_PROFILE_PICTURE, ""))
            .placeholder(getDrawable(R.drawable.ic_profile_placeholder))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE).
            into(binding.ivBnvProfile)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            var menuIdx = 0
            val transaction = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                resList[0] -> {
                    val fragHome = homeFragment
                    menuIdx = 0
                    supportFragmentManager.popBackStackImmediate(tagList[menuIdx], FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    transaction.replace(R.id.frame_layout, fragHome, tagList[menuIdx])

                    setBottomNavigationColorMode(whiteMode = true)

                }
                resList[1] -> {
                    menuIdx = 1
                    supportFragmentManager.popBackStackImmediate(tagList[menuIdx], FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    transaction.replace(R.id.frame_layout, SearchFragment(), tagList[menuIdx])

                    setBottomNavigationColorMode(whiteMode = true)
                }
                resList[2] -> {
                    menuIdx = 2
                    supportFragmentManager.popBackStackImmediate(tagList[menuIdx], FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    transaction.replace(R.id.frame_layout, ReelsFragment(), tagList[menuIdx])

                    setBottomNavigationColorMode(whiteMode = false)

                }
                resList[3] -> {
                    menuIdx = 3
                    supportFragmentManager.popBackStackImmediate(tagList[menuIdx], FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    transaction.replace(R.id.frame_layout, ShoppingFragment(), tagList[menuIdx])

                    setBottomNavigationColorMode(whiteMode = true)
                }
                resList[4] -> {
                    menuIdx = 4
                    supportFragmentManager.popBackStackImmediate(tagList[menuIdx], FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    transaction.replace(R.id.frame_layout, MyPageFragment(), tagList[menuIdx])

                    setBottomNavigationColorMode(whiteMode = true)

                }
            }
            transaction.addToBackStack(tagList[menuIdx])
            transaction.commit()
            transaction.isAddToBackStackAllowed

            return@setOnNavigationItemSelectedListener true
        }

    }

    private fun setBottomNavigationColorMode(whiteMode: Boolean) {
        if (whiteMode) {
            binding.frameLayout.setBackgroundColor(Color.WHITE)
            binding.bottomNavigation.setBackgroundColor(Color.WHITE)
            binding.bottomNavigation.itemIconTintList = getColorStateList(color.black)
            window.statusBarColor = Color.WHITE
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        else {
            binding.frameLayout.setBackgroundColor(Color.BLACK)
            binding.bottomNavigation.setBackgroundColor(Color.BLACK)
            binding.bottomNavigation.itemIconTintList = getColorStateList(color.white)
            window.statusBarColor = Color.BLACK
            window.decorView.systemUiVisibility = 0
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            binding.bottomNavigation.menu.findItem(resList[0]).isChecked = true
            setBottomNavigationColorMode(whiteMode = true)
        }
        super.onBackPressed()
        updateBottomNavigationMenu()
    }

    private fun updateBottomNavigationMenu() {
        for (idx in 0..4) {
            val fragment = supportFragmentManager.findFragmentByTag(tagList[idx])
            if (fragment != null && fragment.isVisible) {
                binding.bottomNavigation.menu.findItem(resList[idx]).isChecked = true
                if (idx == 2) setBottomNavigationColorMode(whiteMode = false)
                else setBottomNavigationColorMode(whiteMode = true)
            }
        }
    }
}