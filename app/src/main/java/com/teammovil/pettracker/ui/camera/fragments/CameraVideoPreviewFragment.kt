package com.teammovil.pettracker.ui.camera.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.teammovil.pettracker.databinding.FragmentCameraVideoPreviewBinding
import java.io.File


class CameraVideoPreviewFragment: Fragment() {

    private lateinit var binding: FragmentCameraVideoPreviewBinding
    private lateinit var videoUrl: String
    private var listener: CameraVideoPreviewListener? = null
    private var navigationListener: NavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraVideoPreviewBinding.inflate(inflater)

        navigationListener?.updateNavigationHome()
        videoUrl = CameraVideoPreviewFragmentArgs.fromBundle(requireArguments()).videoUrl
        setViews()

        return binding.root
    }

    override fun onAttach(context: Context) {
        if(context is CameraVideoPreviewListener){
            listener = context
        }
        if(context is NavigationListener)
            navigationListener = context
        super.onAttach(context)
    }

    private fun setViews() {
        setCameraView()

        binding.videoPreviewBtKeep.setOnClickListener{
            onClickKeep()
        }

        binding.videoPreviewBtDelete.setOnClickListener {
            onClickDelete()
        }
    }

    private fun setCameraView (){
        val mediaController = object : MediaController(requireContext()){
            override fun show() {
                super.show(0)
            }
        }.apply {
            setAnchorView(binding.videoPreviewVvVideo)
        }
        //Location of Media File
        val uri: Uri =
            Uri.parse(videoUrl)
        //Starting VideView By Setting MediaController and URI
        with(binding.videoPreviewVvVideo){
            setMediaController(mediaController)
            setVideoURI(uri)
            requestFocus()
            start()
        }
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable { mediaController.show(0) },
            250
        )
    }

    private fun onClickKeep (){
        listener?.videoAccepted()
    }

    private fun onClickDelete(){
        val imageFile = File(videoUrl)
        if (imageFile.exists()) imageFile.delete()
        view?.findNavController()?.navigateUp()
    }

    interface CameraVideoPreviewListener {
        fun videoAccepted ()
    }
}