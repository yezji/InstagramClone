package com.green.instagramclone.src.main.home

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.green.instagramclone.R.*
import com.green.instagramclone.config.ApplicationClass.Companion.mainActivity
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, layout.fragment_home) {
    lateinit var viewPager: ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.vpHome
        viewPager.adapter = HomeFragmentAdapter(childFragmentManager)
        viewPager.currentItem = 1

        mainActivity.bottomNavigationView.visibility = View.VISIBLE

        val viewPagerChangeListener = ViewPagerChangeListener()
        viewPager.addOnPageChangeListener(viewPagerChangeListener)

    }



    inner class ViewPagerChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            // TODO: 움직임에 따라 BottomNavigationView X좌표 이동
        }

        override fun onPageSelected(position: Int) {
            when (position) {
                1 -> {
                    mainActivity.bottomNavigationView.visibility = View.VISIBLE
                }
                0, 2 -> {
                    mainActivity.bottomNavigationView.visibility = View.GONE
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }
}

