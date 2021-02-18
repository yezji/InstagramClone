package com.green.instagramclone.src.main.home.createfeedorstory

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentChooseFeedOrStoryBinding

class ChooseFeedOrStoryFragment : BaseFragment<FragmentChooseFeedOrStoryBinding>(FragmentChooseFeedOrStoryBinding::bind, R.layout.fragment_choose_feed_or_story) {
    lateinit var viewPager: ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = binding.vpFeedOrStory
        viewPager.adapter = ChooseFeedOrStoryFragmentAdapter(childFragmentManager)
        viewPager.currentItem = 1
        
        // TODO: 각자 프래그먼트에서 드래그 막기
//        viewPager.setOnTouchListener { view, event -> true }
    }

}