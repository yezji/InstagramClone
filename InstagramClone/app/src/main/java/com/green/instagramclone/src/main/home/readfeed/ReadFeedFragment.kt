package com.green.instagramclone.src.main.home.readfeed

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentReadFeedBinding
import com.green.instagramclone.src.main.home.readfeed.models.Feed
import com.green.instagramclone.src.main.home.readfeed.models.UserFeedResponse
import com.green.instagramclone.config.ApplicationClass.Companion.homeFragment
import com.green.instagramclone.config.ApplicationClass.Companion.mainActivity

class ReadFeedFragment : BaseFragment<FragmentReadFeedBinding>(FragmentReadFeedBinding::bind, R.layout.fragment_read_feed),
    ReadFeedView {
    private var feedUser: MutableList<Feed>? = mutableListOf()

    lateinit var ibAdd: ImageButton
    lateinit var ibActivity: ImageButton
    lateinit var ibDm: ImageButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ibAdd = binding.toolbarHome.ibAdd
        ibActivity = binding.toolbarHome.ibLike
        ibDm = binding.toolbarHome.ibDm

        mainActivity.bottomNavigationView.visibility = View.VISIBLE

        binding.rvFeed.layoutManager = LinearLayoutManager(context)

        ReadFeedService(this).tryGetFeed()


        binding.toolbarHome.ibAdd.setOnClickListener {
            homeFragment.viewPager.currentItem = 0
        }
        binding.toolbarHome.ibLike.setOnClickListener {
            // TODO: 프래그먼트로 화면전환
//            val transaction = childFragmentManager.beginTransaction()
        }
        binding.toolbarHome.ibDm.setOnClickListener {
            homeFragment.viewPager.currentItem = 2
        }
    }


    override fun onGetFeedSuccess(response: UserFeedResponse) {
        if (response.isSuccess) {
            feedUser = response.data

            binding.rvFeed.adapter = ReadFeedAdapter(feedUser!!, requireContext())
        }
    }

    override fun onGetFeedFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}