package com.green.instagramclone.src.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.green.instagramclone.src.main.home.createfeedorstory.ChooseFeedOrStoryFragment
import com.green.instagramclone.src.main.home.dm.DmFragment
import com.green.instagramclone.src.main.home.readfeed.ReadFeedFragment

@Suppress("DEPRECATION")
class HomeFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    var items = ArrayList<Fragment>()

    init {
        items.add(ChooseFeedOrStoryFragment())
        items.add(ReadFeedFragment())
        items.add(DmFragment())
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Fragment {
        return items[position]
    }

}