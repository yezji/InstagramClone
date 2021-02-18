package com.green.instagramclone.src.main.home.readfeed

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.green.instagramclone.R
import com.green.instagramclone.src.main.home.readfeed.models.Feed
import com.green.instagramclone.util.ImageVideoView

class ReadFeedAdapter : RecyclerView.Adapter<ReadFeedAdapter.ViewHolder> {
    private var feedUser: MutableList<Feed>
    private var context: Context

    constructor(feedUser: MutableList<Feed>, context: Context) : super() {
        this.feedUser = feedUser
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feed: Feed = feedUser[position]
        Log.d("debugging", "adapter: ${feedUser.get(0).caption}")
        holder.setItem(feed)
    }

    override fun getItemCount(): Int {
        return feedUser.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var ivStoryImg: ImageView = itemView.findViewById(R.id.iv_writer_img)
        private var tvNickname: TextView = itemView.findViewById(R.id.tv_nickname)

        private var imageVideoView: ImageVideoView = itemView.findViewById(R.id.image_video_media)

        private var llFeedLikeTextviews: LinearLayout = itemView.findViewById(R.id.ll_feed_like_textviews)
        private var tvLikedValue: TextView = itemView.findViewById(R.id.tv_liked_value)

        private var tvMyNickname: TextView = itemView.findViewById(R.id.tv_my_nickname)
        private var tvCaption: TextView = itemView.findViewById(R.id.tv_content)

        private var llFeedCommentsExpand: LinearLayout = itemView.findViewById(R.id.ll_feed_comments_expand)
        private var tvCommentsValue: TextView = itemView.findViewById(R.id.tv_comments_value)

        private var llFeedCommentTextviews: LinearLayout = itemView.findViewById(R.id.ll_feed_comment_textviews)
        private var tvfirstCommentNickname: TextView = itemView.findViewById(R.id.tv_writer_nickname)
        private var tvfirstComment: TextView = itemView.findViewById(R.id.tv_comment)

        private var llFeedUploadTime: LinearLayout = itemView.findViewById(R.id.ll_feed_upload_time)
        private var tvUploadedTimeValue: TextView = itemView.findViewById(R.id.tv_uploaded_time_value)

        fun setItem(feedUser: Feed) {
            val storyImgUri = Uri.parse(feedUser.userProfilePicture)
            // TODO: url 확실하게
            ivStoryImg.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(context).load(storyImgUri.toString()).into(ivStoryImg)


            val mediaUri = Uri.parse(feedUser.mediaURL)
            imageVideoView.setUrl(mediaUri.toString())



            val likeCount = feedUser.likeCount
            tvLikedValue.text = likeCount.toString()
            if (likeCount > 0) llFeedLikeTextviews.visibility = View.VISIBLE
            else llFeedLikeTextviews.visibility = View.GONE

            val myNicknameText = feedUser.userNickname
            tvNickname.text = myNicknameText
            tvMyNickname.text = myNicknameText

            val myCaptionText = feedUser.caption
            tvCaption.text = myCaptionText

            val commentCount = feedUser.commentCount
            tvCommentsValue.text = commentCount.toString()
            if (commentCount > 0) {
                llFeedCommentsExpand.visibility = View.VISIBLE
                llFeedCommentTextviews.visibility = View.VISIBLE

                tvfirstCommentNickname.text = feedUser.firstUserNickname
                tvfirstComment.text = feedUser.firstCommentText
            }
            else {
                llFeedCommentsExpand.visibility = View.GONE
                llFeedCommentTextviews.visibility = View.GONE
            }

        }
    }
}
