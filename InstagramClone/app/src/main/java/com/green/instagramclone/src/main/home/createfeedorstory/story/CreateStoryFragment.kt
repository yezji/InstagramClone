package com.green.instagramclone.src.main.home.createfeedorstory.story

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.*
import android.media.ExifInterface
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.util.SparseIntArray
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.green.instagramclone.R
import com.green.instagramclone.config.BaseFragment
import com.green.instagramclone.databinding.FragmentCreateStoryBinding

class CreateStoryFragment : BaseFragment<FragmentCreateStoryBinding>(FragmentCreateStoryBinding::bind, R.layout.fragment_create_story) {
    private val TAG = javaClass.simpleName
    // https://makasti.tistory.com/73

    private lateinit var surfaceViewHolder: SurfaceHolder
    private lateinit var handler: Handler
    private lateinit var imageReader: ImageReader
    private lateinit var cameraDevice: CameraDevice
    private lateinit var previewBuilder: CaptureRequest.Builder
    private lateinit var session: CameraCaptureSession

    var cameraId = CAMERA_BACK

    companion object {
        const val CAMERA_BACK = "0"
//        const val CAMERA_FRONT = "1"
        private val ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initView()
    }

    fun initView() {
        // 화면 켜짐 유지
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )

        surfaceViewHolder = binding.surfaceViewStory.holder
        surfaceViewHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {
                initCameraAndPreview()
            }

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraDevice.close()
            }
        })
    }

    fun initCameraAndPreview() {
        val handlerThread = HandlerThread("CAMERA2")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        openCamera()
    }

    private fun openCamera() {
        try {
            val cameraManager = requireContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            val characteristics = cameraManager.getCameraCharacteristics(cameraId)
//            val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) return
            cameraManager.openCamera(this.cameraId, this.deviceStateCallback, this.handler)
        }
        catch (e: CameraAccessException) {
            Log.e(TAG, "can't open camera")
        }
    }
    private val deviceStateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            takePreview()
        }

        override fun onClosed(camera: CameraDevice) {
            cameraDevice.close()
        }

        override fun onDisconnected(camera: CameraDevice) {}

        override fun onError(camera: CameraDevice, error: Int) {
            Log.e(TAG, "can't open camera: $error")
        }
    }

    fun takePreview() {
        previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        previewBuilder.addTarget(surfaceViewHolder.surface)
        cameraDevice.createCaptureSession(listOf(surfaceViewHolder.surface, imageReader.surface),
        sessionPreviewStateCallback, handler)
    }

    private val sessionPreviewStateCallback = object : CameraCaptureSession.StateCallback() {
        override fun onConfigured(sess: CameraCaptureSession) {
            session = sess
            // 필요한 경우 오토포커싱, 플래시 설정 추가 가능
        }

        override fun onConfigureFailed(p0: CameraCaptureSession) {
            Log.e(TAG, "카메라 구성 실패")
        }
    }
}