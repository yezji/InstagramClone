package com.green.instagramclone.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestListener
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.green.instagramclone.R
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ImageVideoView : LinearLayout {
    private lateinit var imageView: ImageView
    private lateinit var customExoPlayerView: CustomExoPlayerView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setUrl(url: String) {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (isVideo(url)) {
            customExoPlayerView = CustomExoPlayerView(context)
            customExoPlayerView.layoutParams = params
            addView(customExoPlayerView)
            customExoPlayerView.initializePlayer(url)
        }
        else {
            imageView = ImageView(context)
            imageView.layoutParams = params
            imageView.adjustViewBounds = true
            addView(imageView)

            var myUrl = url
            if (url.contains("alt=")) {
                myUrl = myUrl.split("alt=").first()
                myUrl = myUrl.substring(0, myUrl.length-1)
                myUrl += "?alt=media"
                Log.d("myurl", myUrl.toString())
            }

            // TODO : placeholder 이미지 교체하기
            Glide.with(context)
                .load(myUrl)
                .downsample(DownsampleStrategy.AT_MOST)
                .override(this.width, this.height)
                .centerCrop()
                .placeholder(R.drawable.btn_bnv_home)
                .error(R.drawable.btn_bnv_search)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)

        }

    }

    private fun isVideo(path: String) : Boolean {
        return when (getExtension(path)) {
            "MP4", "mp4", "MOV", "mov", "AVI", "avi",
            "MKV", "mkv", "WMV", "wmv", "TS", "ts",
            "TP", "tp", "FLV", "flv", "3GP", "3gp",
            "MPG", "mpg", "MPEG", "mpeg", "MPE", "mpe",
            "ASF", "asf", "ASX", "asx", "DAT", "dat",
            "RM", "rm" -> true
            else -> false
        }
    }

    private fun getExtension(url: String) : String {
        return url.substring(url.lastIndexOf(".") + 1, url.length)
    }
}

class CustomExoPlayerView : PlayerView {
    private lateinit var player: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DefaultHttpDataSourceFactory

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun initializePlayer(url: String) {
        /*if (trackSelector != null) {
            Log.i("DATA", "trankSelector : not null");
        }
        else {
            Log.i("DATA", "trankSelector : null");
        }
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)*/

        mediaDataSourceFactory = DefaultHttpDataSourceFactory(Util.getUserAgent(context, "mediaPlayer"))
        val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse(url))

        player = ExoPlayerFactory.newSimpleInstance(context)
        player.prepare(mediaSource, false, false)
        player.playWhenReady = true

        setShutterBackgroundColor(Color.TRANSPARENT)
        setPlayer(player)
        requestFocus()

    }

}