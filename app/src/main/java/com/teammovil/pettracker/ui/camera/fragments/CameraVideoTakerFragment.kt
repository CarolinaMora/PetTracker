package com.teammovil.pettracker.ui.camera.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentCameraVideoTakerBinding
import com.teammovil.pettracker.ui.camera.CameraConfiguration
import com.teammovil.pettracker.util.FileUtils
import java.io.File


class CameraVideoTakerFragment: Fragment() {

    private val TAG = "CameraVideoTaker"

    private lateinit var binding: FragmentCameraVideoTakerBinding
    private lateinit var videoCapture: VideoCapture
    private lateinit var cameraConfiguration: CameraConfiguration
    private lateinit var maxCaptureHandler: Handler
    private lateinit var maxCaptureRunnable: Runnable
    private var videoIsRecording = false
    private var navigationListener: NavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraVideoTakerBinding.inflate(inflater)

        navigationListener?.updateNavigationHome()
        cameraConfiguration = CameraVideoTakerFragmentArgs.fromBundle(requireArguments()).configuration
        setViews()

        return binding.root
    }

    override fun onAttach(context: Context) {
        if(context is NavigationListener)
            navigationListener = context
        super.onAttach(context)
    }

    private fun setViews() {
        startCamera()
        binding.videoTakerBtRecord.setOnClickListener {
            onClickRecordStopVideo()
        }

        maxCaptureHandler = Handler(Looper.getMainLooper())
        maxCaptureRunnable = Runnable { stopVideo() }
    }



    @SuppressLint("RestrictedApi")
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .setMaxResolution(Size(cameraConfiguration.maxResolutionWidth, cameraConfiguration.maxResolutionHeight))
                .build()
                .also {
                    it.setSurfaceProvider(binding.videoTakerPreviewView.surfaceProvider)
                }

            videoCapture = VideoCapture.Builder()
                .setVideoFrameRate(cameraConfiguration.videoFrameRate)
                .setBitRate(cameraConfiguration.videoBitRate)
                .setMaxResolution(Size(cameraConfiguration.maxResolutionWidth, cameraConfiguration.maxResolutionHeight))
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, videoCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    @SuppressLint("MissingPermission", "RestrictedApi")
    private fun onClickRecordStopVideo(){

        if(!videoIsRecording) {

            val videoFile = File(cameraConfiguration.fileName)
            if (videoFile.exists()) FileUtils.deleteFile(videoFile.absolutePath)
            videoFile.createNewFile()
            val outputOptions = VideoCapture.OutputFileOptions.Builder(videoFile).build()

            videoIsRecording = true
            binding.videoTakerBtRecord.setImageResource(R.drawable.ic_stop_video)
            val systemCurrTime: Long = SystemClock.elapsedRealtime()
            binding.videoTakerTvTime.setBase(systemCurrTime)
            binding.videoTakerTvTime.start()
            maxCaptureHandler.postDelayed(maxCaptureRunnable, cameraConfiguration.maxVideoTimeInSeconds * 1000L)
            videoCapture.startRecording(
                outputOptions,
                ContextCompat.getMainExecutor(activity),

                object : VideoCapture.OnVideoSavedCallback {
                    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                        val fileSize = videoFile.length() / 1024
                        Log.e(TAG, "onVideoSaved size: $fileSize")
                        videoCaptured()
                    }

                    override fun onError(
                        videoCaptureError: Int,
                        message: String,
                        cause: Throwable?
                    ) {
                        stopVideo()
                        Log.e("TAG", "onError->$message")
                    }
                })
        }
        else{
            stopVideo()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun stopVideo (){
        videoIsRecording = false
        maxCaptureHandler.removeCallbacks(maxCaptureRunnable)
        binding.videoTakerTvTime.stop()
        videoCapture.stopRecording()
    }

    private fun videoCaptured (){
        cameraConfiguration.fileName?.let {
            val action = CameraVideoTakerFragmentDirections.actionCameraVideoTakerFragmentToCameraVideoPreviewFragment(
                it
            )
            view?.findNavController()?.navigate(action)
        }
    }
}