package com.teammovil.pettracker.ui.camera

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.ActivityCameraBinding
import com.teammovil.pettracker.ui.camera.fragments.CameraPhotoPreviewFragment
import com.teammovil.pettracker.ui.camera.fragments.CameraVideoPreviewFragment
import com.teammovil.pettracker.ui.camera.fragments.NavigationListener

class CameraActivity: AppCompatActivity(),
    CameraPhotoPreviewFragment.CameraPhotoPreviewListener,
    CameraVideoPreviewFragment.CameraVideoPreviewListener,
    NavigationListener
{

    private lateinit var binding: ActivityCameraBinding
    private lateinit var navControllerCamera: NavController
    private lateinit var configuration: CameraConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tbToolbar?.context?.setTheme(R.style.ThemeOverlay_AppCompat_Dark_ActionBar)

        getArgs()
        setupNavigation()
    }

    private fun getArgs (){
        configuration = CameraActivityArgs.fromBundle(intent.extras!!).configuration
    }

    private fun setupNavigation() {
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_camera) as NavHostFragment)
        navControllerCamera = navHostFragment.navController
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.camera_navigation)
        graph.startDestination = getDestination()
        navControllerCamera.setGraph(graph, intent.extras)

        updateNavigation()
    }

    private fun updateNavigation (){
        val navController = this.findNavController(R.id.nav_host_camera)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.tbToolbar, navController, appBarConfiguration)
    }

    override fun updateNavigationHome (){
        setSupportActionBar(binding.tbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_camera)

        if(navController.graph.startDestination == navController.currentDestination?.id){
            super.onBackPressed()
        }
        else {
            val appBarConfiguration = AppBarConfiguration(navController.graph)
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }
        return true
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }

    private fun getDestination (): Int {
        return when (configuration.mediaType){
            CameraConstants.REQUEST_VIDEO_CAPTURE -> R.id.cameraVideoTakerFragment
            else -> R.id.cameraPhotoTakerFragment
        }
    }

    override fun photoAccepted() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun videoAccepted() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}