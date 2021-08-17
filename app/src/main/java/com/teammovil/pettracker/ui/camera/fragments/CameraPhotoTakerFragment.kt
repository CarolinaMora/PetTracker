package com.teammovil.pettracker.ui.camera.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.teammovil.pettracker.databinding.FragmentCameraPhotoTakerBinding
import com.teammovil.pettracker.ui.camera.CameraConfiguration
import com.teammovil.pettracker.util.FileUtils
import java.io.File

class CameraPhotoTakerFragment: Fragment() {
    private val TAG = "CameraPhotoTaker"
    private lateinit var binding: FragmentCameraPhotoTakerBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraConfiguration: CameraConfiguration
    private var navigationListener: NavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraPhotoTakerBinding.inflate(inflater)

        navigationListener?.updateNavigationHome()
        cameraConfiguration = CameraPhotoTakerFragmentArgs.fromBundle(requireArguments()).configuration
        setViews()

        return binding.root
    }

    override fun onAttach(context: Context) {
        if(context is NavigationListener)
            navigationListener = context
        super.onAttach(context)
    }

    private fun setViews(){
        startCamera()
        binding.photoTakerBtTake.setOnClickListener {
            takePhoto()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.photoTakerPreviewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .setMaxResolution(Size(cameraConfiguration.maxResolutionWidth, cameraConfiguration.maxResolutionHeight))
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {

        // Create time-stamped output file to hold the image
        val photoFile = File(cameraConfiguration.fileName)
        if (photoFile.exists()) FileUtils.deleteFile(photoFile.absolutePath)
        photoFile.createNewFile()
        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val fileSize = photoFile.length() / 1024
                    val msg = "Photo capture succeeded: $savedUri file size: $fileSize"
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    photoTaken(photoFile.absolutePath)
                }
            })
    }

    private fun photoTaken (imageUrl: String){
        val action = CameraPhotoTakerFragmentDirections.actionCameraPhotoTakerFragmentToCameraPhotoPreviewFragment(imageUrl)
        view?.findNavController()?.navigate(action)
    }
}