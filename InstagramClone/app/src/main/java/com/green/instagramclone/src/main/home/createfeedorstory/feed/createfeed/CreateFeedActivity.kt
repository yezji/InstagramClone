package com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.green.instagramclone.config.ApplicationClass.Companion.USER_NICKNAME_IDX
import com.green.instagramclone.config.ApplicationClass.Companion.sSharedPreferences
import com.green.instagramclone.config.BaseActivity
import com.green.instagramclone.config.BaseResponse
import com.green.instagramclone.databinding.ActivityCreateFeedBinding
import com.green.instagramclone.src.main.MainActivity
import com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed.models.MediaURL
import com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed.models.PostCreateFeedRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ISO_DATE_TIME
import java.util.ArrayList

class CreateFeedActivity : BaseActivity<ActivityCreateFeedBinding>(ActivityCreateFeedBinding::inflate),
    CreateFeedView {
    private val TAG = javaClass.simpleName
    private var storage: FirebaseStorage? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receiveIntent = intent
        val strImageList: ArrayList<String>? = receiveIntent.getStringArrayListExtra("imageList")

        val downloadUriList = ArrayList<MediaURL>()

        val bitmapList = mutableListOf<Bitmap>()

        // firebase storage
        storage = FirebaseStorage.getInstance()
        var storageRef: StorageReference?

        for (strImage in strImageList!!) {
            bitmapList.add(BitmapFactory.decodeFile(strImage))
        }
        binding.ivImage.setImageBitmap(bitmapList[0])
        binding.ivImage.scaleType = ImageView.ScaleType.CENTER_CROP

        if (strImageList.size > 1) {
            binding.ivMulti.visibility = View.VISIBLE
        }
        else {
            binding.ivMulti.visibility = View.GONE
        }


        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.ibUpload.setOnClickListener {
            for (strImage in strImageList) {
                var fileName = strImage.split("/").last()
                val splited = fileName.split(".")
                fileName = splited.first() + LocalDateTime.now().format(ISO_DATE_TIME).split('.').first() + "." + splited.last()

                // upload to firebase
                storageRef = storage?.reference?.child("media")?.child(fileName)

                val uploadTask = storageRef?.putFile(Uri.parse("file://$strImage"))
                uploadTask?.continueWithTask { task ->
                    if (task.isSuccessful) {
                        task.exception?.let {
                            showCustomToast("이미지 업로드 실패")
                            throw it
                        }
                    }
//                    storageRef?.downloadUrl
                    storage?.reference?.child("media")?.child(fileName)?.downloadUrl
                }?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        downloadUriList.add(MediaURL(downloadUri.toString()))

                        if (strImageList.size == downloadUriList.size) {
                            Log.d(TAG, "download imageList size: ${downloadUriList.size}")
                            for (idx in 0 until downloadUriList.size) Log.d(TAG, "download imageList elem: $idx, ${downloadUriList[idx]}")

                            val postRequest = PostCreateFeedRequest(
                                userNickNameIdx = sSharedPreferences.getInt(USER_NICKNAME_IDX, 0),
                                caption = binding.etContent.text.toString(),
                                mediaIdx = downloadUriList.size,
                                mediaList = downloadUriList,
                                feedCreateDate = "default",
                                feedUpdateDate = "default"
                            )

                            CreateFeedService(this).tryPostCreateFeed(postRequest)
                        }
                    }
                    else {
                        showCustomToast("이미지 uri 가져오기 실패")
                    }
                }
            }

        }
    }

    override fun onPostCreateFeedSuccess(response: BaseResponse) {
        if (response.isSuccess) {
            showCustomToast(response.message.toString())

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
        else {
            showCustomToast(response.message.toString())
        }
    }

    override fun onPostCreateFeedFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}