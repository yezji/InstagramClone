package com.green.instagramclone.util

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

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

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setUrl(url: String) {
        val params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (isVideo(url)) {
            customExoPlayerView = CustomExoPlayerView(context)
            customExoPlayerView.layoutParams = params
            addView(customExoPlayerView)
            customExoPlayerView.initializePlayer(url)
        }
        else {
            imageView = ImageView(context)
            imageView.layoutParams = params
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            addView(imageView)
            Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView)
        }
    }

    private fun isVideo(path: String) : Boolean {
        val extension = getExtension(path)

        return when (extension) {
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