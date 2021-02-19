package com.green.instagramclone.src.main.home.readfeed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.green.instagramclone.R
import com.green.instagramclone.config.ApplicationClass.Companion.USER_PROFILE_PICTURE
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentReadFeedBinding
import com.green.instagramclone.src.main.home.readfeed.models.Feed
import com.green.instagramclone.src.main.home.readfeed.models.AllUserFeedsResponse
import com.green.instagramclone.config.ApplicationClass.Companion.homeFragment
import com.green.instagramclone.config.ApplicationClass.Companion.mainActivity
import com.green.instagramclone.config.ApplicationClass.Companion.sSharedPreferences

class ReadFeedFragment : BaseFragment<FragmentReadFeedBinding>(FragmentReadFeedBinding::bind, R.layout.fragment_read_feed),
    ReadFeedView {
    private var feedUser: MutableList<Feed>? = mutableListOf()

    lateinit var ibAdd: ImageButton
    lateinit var ibActivity: ImageButton
    lateinit var ibDm: ImageButton

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ibAdd = binding.toolbarHome.ibAdd
        ibActivity = binding.toolbarHome.ibLike
        ibDm = binding.toolbarHome.ibDm

        mainActivity.bottomNavigationView.visibility = View.VISIBLE

        binding.rvFeed.layoutManager = LinearLayoutManager(context)

        ReadFeedService(this).tryGetFeed()


        // buttons for toolbar
        binding.toolbarHome.ibAdd.setOnClickListener {
            homeFragment.viewPager.currentItem = 0
        }
        binding.toolbarHome.ibLike.setOnClickListener {
            // TODO: 프래그먼트로 화면전환
            // val transaction = childFragmentManager.beginTransaction()
        }
        binding.toolbarHome.ibDm.setOnClickListener {
            homeFragment.viewPager.currentItem = 2
        }

        // set my profile at story
        Glide.with(context!!)
            .load(sSharedPreferences.getString(USER_PROFILE_PICTURE, ""))
            .placeholder(context!!.getDrawable(R.drawable.ic_profile_placeholder))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.svStory.ivWriterImg)

    }

    override fun onStart() {
        super.onStart()

        refreshAdapter()
    }


    fun refreshAdapter() {
        ReadFeedService(this).tryGetFeed()
    }

    override fun onGetFeedSuccess(response: AllUserFeedsResponse) {
        if (response.isSuccess) {
            feedUser!!.clear()
            feedUser = response.data

            feedUser!!.reverse()

            binding.rvFeed.adapter = ReadFeedAdapter(feedUser!!, requireContext())
        }
    }

    override fun onGetFeedFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}