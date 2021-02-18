package com.green.instagramclone.src.main.home

import android.os.Bundle
import android.view.View
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}