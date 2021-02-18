package com.green.instagramclone.src.main.home.createfeedorstory.feed

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentCreateFeedBinding
import com.green.instagramclone.src.main.MainActivity
import com.green.instagramclone.src.main.home.createfeedorstory.feed.createfeed.CreateFeedActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

@Suppress("DEPRECATION", "NAME_SHADOWING")
class CreateFeedFragment : BaseFragment<FragmentCreateFeedBinding>(FragmentCreateFeedBinding::bind, R.layout.fragment_create_feed) {
    private val TAG = javaClass.simpleName

    private val REQUEST_READ_EXTERNAL_STORAGE = 1000

    private var uriList = mutableListOf<String>()
    private var selectPosition = 0
    private var multipleMode = false
    private var selectedPairList = mutableListOf<Pair<Int, Int>>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 저장소 접근 권한
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 이전에 이미 권한이 거부되었을 때 설명
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requireContext().alert("사진 정보를 얻으려면 외부 저장소 권한이 필수로 요구됩니다. ", "권한이 필요한 이유") {
                    yesButton {
                        // 권한 요청
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                    noButton {
                        // do nothing
                    }
                }.show()
            }
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_EXTERNAL_STORAGE
                )
            }
        }
        else {
            // 권한이 이미 허용 되었을 때
            getAllPhotos()
        }

        binding.checkMultiselect.setOnClickListener {
            this.multipleMode = !this.multipleMode
            selectedPairList.clear()
            selectedPairList.add(Pair(1, this.selectPosition))

            /*if (this.multipleMode) {
                // TODO : 숫자 띄우기
            }
            else {
                // TODO : 숫자 사라지기
            }*/
        }

        binding.ibCancle.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }

        binding.ibNext.setOnClickListener {
            if (!multipleMode) {
                val intent = Intent(context, CreateFeedActivity::class.java)
                val strImage = uriList[selectedPairList[0].second]
                intent.putExtra("imageList", arrayListOf(strImage))
                startActivity(intent)
            }
            else {
                if (selectedPairList.size > 0) {
                    val intent = Intent(context, CreateFeedActivity::class.java)
                    val selectedList = arrayListOf<String>()
                    for (selectedPair in selectedPairList) {
                        selectedList.add(uriList[selectedPair.second])
                    }
                    intent.putExtra("imageList", selectedList)
                    startActivity(intent)
                }
                else {
                    // TODO: 토스트를 다이얼로그로 대체
                    showCustomToast("사진을 1개 이상 선택해주세요.")
                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        getAllPhotos()
                    }
                }
                else {
                    Log.d(TAG, "permission access denied")
                }
                return
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getAllPhotos() {
        val cursor: Cursor? = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )

        if (cursor != null) {
            // 커서 먼저 돌기
            while (cursor.moveToNext()) {
                // 사진 경로 Uri 가져오기
                val uri = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriList.add(uri)
            }
            cursor.moveToFirst()

            cursor.close()

        }

        val adapter = PickGalleryAdapter(requireContext(), uriList)
        binding.gridView.numColumns = 4
        binding.gridView.adapter = adapter
        binding.gridView.setSelection(0)

        val bitmap = BitmapFactory.decodeFile(uriList.get(0))
        binding.ivSelect.setImageBitmap(bitmap)


        binding.gridView.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            val bitmap = BitmapFactory.decodeFile(uriList[position])

            this.selectPosition = position
            binding.ivSelect.setImageBitmap(bitmap)
            // TODO: 흰색 불투명 이미지뷰 덧씌우고 해제 처리

            if (!multipleMode) {
                selectedPairList.clear()
                selectedPairList.add(Pair(1, position))
            }
            else {
                var isSelected = false
                for (idx in 0 until selectedPairList.size) {
                    if (selectedPairList[idx].second == position) {
                        // if selected this position then remove from selected array
                        isSelected = true
                        selectedPairList.removeAt(idx)
                        // pull indexes
                        if (idx >= selectedPairList.size-1) break
                        for (posRemove in idx until selectedPairList.size) {
                            val first = selectedPairList[idx].first
                            val second = selectedPairList[idx].second
                            selectedPairList.add(Pair(first, second))
                            selectedPairList.removeAt(idx)
                        }
                    }
                }
                if (!isSelected) {
                    selectedPairList.add(Pair(selectedPairList.size+1, position))
                }

            }
        }

    }
}