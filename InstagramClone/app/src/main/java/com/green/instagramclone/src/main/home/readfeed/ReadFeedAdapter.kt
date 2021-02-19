package com.green.instagramclone.src.main.home.readfeed

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.green.instagramclone.R
import com.green.instagramclone.config.ApplicationClass.Companion.USER_NICKNAME_IDX
import com.green.instagramclone.config.ApplicationClass.Companion.sSharedPreferences
import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.src.main.home.readfeed.models.*
import com.green.instagramclone.util.ImageVideoView
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReadFeedAdapter(private var feedUser: MutableList<Feed>, private var context: Context) :
    RecyclerView.Adapter<ReadFeedAdapter.ViewHolder>() {

    private var feedLikedUser: MutableList<LikedUsers> = mutableListOf()
    private var feedLikedIdx: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed: Feed = feedUser[position]
        holder.setItem(feed)
    }

    override fun getItemCount(): Int {
        return feedUser.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), FeedLikeView {
        private var ivStoryImg: ImageView = itemView.findViewById(R.id.iv_writer_img)
        private var tvNickname: TextView = itemView.findViewById(R.id.tv_nickname)

        private var ibFeedMenu: ImageButton = itemView.findViewById(R.id.ib_feed_menu)

        private var imageVideoView: ImageVideoView = itemView.findViewById(R.id.image_video_media)

        private var ibLike: ImageButton = itemView.findViewById(R.id.ib_like)
        private var ibReply: ImageButton = itemView.findViewById(R.id.ib_reply)
        private var ibDm: ImageButton = itemView.findViewById(R.id.ib_dm)
        private var ibSaved: ImageButton = itemView.findViewById(R.id.ib_saved)

        private var llFeedLikeTextviews: LinearLayout = itemView.findViewById(R.id.ll_feed_like_textviews)
        private var tvLikedValue: TextView = itemView.findViewById(R.id.tv_liked_value)

        private var tvMyNickname: TextView = itemView.findViewById(R.id.tv_my_nickname)
        private var tvCaption: TextView = itemView.findViewById(R.id.tv_content)

        private var llFeedCommentsExpand: LinearLayout = itemView.findViewById(R.id.ll_feed_comments_expand)
        private var tvCommentsValue: TextView = itemView.findViewById(R.id.tv_comments_value)

        private var llFeedCommentTextviews: LinearLayout = itemView.findViewById(R.id.ll_feed_comment_textviews)
        private var tvfirstCommentNickname: TextView = itemView.findViewById(R.id.tv_writer_nickname)
        private var tvfirstComment: TextView = itemView.findViewById(R.id.tv_comment)

        private var tvUploadedTimeValue: TextView = itemView.findViewById(R.id.tv_uploaded_time_value)

        @RequiresApi(Build.VERSION_CODES.O)
        fun setItem(feedUser: Feed) {
            feedLikedIdx = feedUser.feedIdx
            FeedLikeService(this).tryGetFeedLikedUsers(feedLikedIdx)

            val storyImgUri = Uri.parse(feedUser.userProfilePicture)
            ivStoryImg.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context)
                .load(storyImgUri.toString())
                .placeholder(context!!.getDrawable(R.drawable.ic_profile_placeholder))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivStoryImg)

            ibFeedMenu.setOnClickListener {
                // TODO : Bottom Sheet 추가
            }

            // TODO : 뷰페이저2 사용해서 여러장 보이기
            val mediaCount = feedUser.mediaIdx
            val mediaList = feedUser.mediaURL.split(",")
            var mediaUri: Uri? = null
            for (mediaURL in mediaList) {
                mediaUri = Uri.parse(mediaURL)
            }
            imageVideoView.setUrl(mediaUri.toString())


            // 현재 피드를 내가 좋아요했는지 체크
            var isLikedFeed = false
            for (user in feedLikedUser) {
                if (user.userNickNameIdx == sSharedPreferences.getInt(USER_NICKNAME_IDX, 0)) isLikedFeed = true
            }
            setLikeIcon(isLikedFeed)

            ibLike.setOnClickListener {
                if (!isLikedFeed) {
                    FeedLikeService(this).tryPostFeedLike(
                        feedIdx = feedLikedIdx,
                        params = PostCreateLikeFeedRequest(sSharedPreferences.getInt(USER_NICKNAME_IDX, 0))
                    )
                    isLikedFeed = !isLikedFeed
                    setLikeIcon(isLikedFeed)
                }
                else {
                    FeedLikeService(this).tryPatchFeedLike(
                        feedIdx = feedLikedIdx,
                        userNickNameIdx = sSharedPreferences.getInt(USER_NICKNAME_IDX, 0)
                    )
                    isLikedFeed = !isLikedFeed
                    setLikeIcon(isLikedFeed)
                }
                notifyDataSetChanged()
            }

            val likeCount = feedUser.likeCount?:0
            tvLikedValue.text = likeCount.toString()
            if (likeCount > 0) llFeedLikeTextviews.visibility = View.VISIBLE
            else llFeedLikeTextviews.visibility = View.GONE

            val myNicknameText = feedUser.userNickName
            tvNickname.text = myNicknameText
            tvMyNickname.text = myNicknameText

            val myCaptionText = feedUser.caption
            tvCaption.text = myCaptionText

            val commentCount = feedUser.commentCount?:0
            tvCommentsValue.text = commentCount.toString()
            if (commentCount > 0) {
                llFeedCommentsExpand.visibility = View.VISIBLE
                llFeedCommentTextviews.visibility = View.VISIBLE

                tvfirstCommentNickname.text = feedUser.firstUserNickName
                tvfirstComment.text = feedUser.firstCommentText
            }
            else {
                llFeedCommentsExpand.visibility = View.GONE
                llFeedCommentTextviews.visibility = View.GONE
            }

            val now = LocalDateTime.now()
            val uploaded = LocalDateTime.parse(feedUser.feedCreateDate, DateTimeFormatter.ISO_DATE_TIME)
            val time = Duration.between(uploaded, now).seconds

            tvUploadedTimeValue.text = when {
                time < 60 -> "${time.toInt()}초 "
                time/60 < 60 -> "${(time/60).toInt()}분 "
                time/(60*60) < 24 -> "${(time/(60*60)).toInt()}시간 "
                time/(60*60*24) < 7 -> "${(time/(60*60*24)).toInt()}일 "
                time/(60*60*24*7) < 4 -> "${(time/(60*60*24*7)).toInt()}주 "
                time/(60*60*24*7*4) < 12 -> "${(time/(60*60*24*7*4)).toInt()}달 "
                else -> "${(time/(60*60*24*7*4*12)).toInt()}년 "
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun setLikeIcon(isLikedFeed: Boolean) {
            if (!isLikedFeed) ibLike.setImageDrawable(context.getDrawable(R.drawable.ic_feed_liked))
            else ibLike.setImageDrawable(context.getDrawable(R.drawable.ic_feed_like))
        }

        override fun onGetFeedLikedUsersSuccess(response: FeedLikedUsersResponse) {
            if (response.isSuccess) {
                feedLikedUser.clear()
                feedLikedUser = response.data
                // TODO : 좋아요한 유저들 목록 위젯 처리
            }
        }

        override fun onGetFeedLikedUsersFailure(message: String) {
            Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
        }

        override fun onPostFeedLikeSuccess(response: BaseResponse) {
            when (response.code) {
                1000 -> {
                    // TODO : 토스트 메세지 지우기
                    Toast.makeText(context, "좋아요 생성 : ${response.message}", Toast.LENGTH_SHORT).show()
                }
                3508 -> {
                    FeedLikeService(this).tryPatchFeedLike(
                        feedIdx = feedLikedIdx,
                        userNickNameIdx = sSharedPreferences.getInt(USER_NICKNAME_IDX, 0)
                    )
                }
            }
        }

        override fun onPostFeedLikeFailure(message: String) {
            Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
        }

        override fun onPatchFeedLikeSuccess(response: BaseResponse) {
            // TODO : 토스트 메세지 지우기
            Toast.makeText(context, "좋아요 수정: ${response.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onPatchFeedLikeFailure(message: String) {
            Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
        }

    }

}
