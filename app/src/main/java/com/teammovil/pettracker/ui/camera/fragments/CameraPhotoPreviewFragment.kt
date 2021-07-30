package com.teammovil.pettracker.ui.camera.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentCameraPhotoPreviewBinding
import java.io.File

class CameraPhotoPreviewFragment: Fragment() {

    private lateinit var binding: FragmentCameraPhotoPreviewBinding
    private var imageUrl: String? = null
    private var listener: CameraPhotoPreviewListener? = null
    private var navigationListener: NavigationListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraPhotoPreviewBinding.inflate(inflater)

        navigationListener?.updateNavigationHome()
        imageUrl = CameraPhotoPreviewFragmentArgs.fromBundle(requireArguments()).imageUrl
        setViews()

        return binding.root
    }

    override fun onAttach(context: Context) {
        if(context is CameraPhotoPreviewListener)
            listener = context
        if(context is NavigationListener)
            navigationListener = context
        super.onAttach(context)
    }

    private fun setViews(){

        //Image
        Glide.with(requireContext())
            .load(imageUrl)
            .signature(ObjectKey(System.currentTimeMillis().toString()))
            .apply(
                RequestOptions.centerCropTransform()
                    .placeholder(R.drawable.ic_insert_photo)
                    .error(R.drawable.ic_insert_photo)
                    .fallback(R.drawable.ic_insert_photo)
            )
            .into(binding.photoPreviewIvImage)

        //Buttons
        binding.photoPreviewBtKeep.setOnClickListener {
            onClickKeep()
        }
        binding.photoPreviewBtDelete.setOnClickListener {
            onClickDelete()
        }
    }

    private fun onClickKeep (){
        listener?.photoAccepted()
    }

    private fun onClickDelete(){
        imageUrl?.let {
            val imageFile = File(it)
            if (imageFile.exists()) imageFile.delete()
        }
        view?.findNavController()?.navigateUp()
    }

    interface CameraPhotoPreviewListener {
        fun photoAccepted ()
    }
}