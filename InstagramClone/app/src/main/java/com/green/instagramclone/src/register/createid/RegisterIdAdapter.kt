package com.green.instagramclone.src.register.createid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.green.instagramclone.src.register.createid.emailcheck.RegisterEmailFragment
import com.green.instagramclone.src.register.createid.phonecheck.RegisterPhoneFragment
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class RegisterIdAdapter(sfm: FragmentManager) : FragmentStatePagerAdapter(sfm) {
    private var items = ArrayList<Fragment>()

    init {
        items.add(RegisterPhoneFragment())
        items.add(RegisterEmailFragment())
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }
}