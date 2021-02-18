package com.green.instagramclone.src.main.home.fcmactivties

import android.os.Bundle
import android.view.View
import com.green.instagramclone.R
import com.green.instagramclone.config.ApplicationClass.Companion.mainActivity
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentActivitiesBinding

class ActivitiesFragment : BaseFragment<FragmentActivitiesBinding>(FragmentActivitiesBinding::bind, R.layout.fragment_activities) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.bottomNavigationView.visibility = View.VISIBLE
    }

}