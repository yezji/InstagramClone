package com.green.instagramclone.src.main.home.createfeedorstory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.green.instagramclone.src.main.home.createfeedorstory.feed.CreateFeedFragment
import com.green.instagramclone.src.main.home.createfeedorstory.live.CreateLiveFragment
import com.green.instagramclone.src.main.home.createfeedorstory.reels.CreateReelsFragment
import com.green.instagramclone.src.main.home.createfeedorstory.story.CreateStoryFragment

@Suppress("DEPRECATION")
class ChooseFeedOrStoryFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var items = ArrayList<Fragment>()

    init {
        items.add(CreateFeedFragment())
        items.add(CreateStoryFragment())
        items.add(CreateReelsFragment())
        items.add(CreateLiveFragment())
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

}