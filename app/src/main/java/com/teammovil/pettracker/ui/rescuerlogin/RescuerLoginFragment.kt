package com.teammovil.pettracker.ui.rescuerlogin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.database.dataaccess.RescuerStorageDataAccessDataBaseImpl
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.services.RescuerExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentRescuerLoginBinding
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.common.UserView
import com.teammovil.usecases.loginrescuer.LoginRescuerUseCase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RescuerLoginFragment : Fragment() {

    private val viewModel: RescuerLoginViewModel by viewModels()
    private lateinit var binding: FragmentRescuerLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRescuerLoginBinding.inflate(layoutInflater)

        setListener()
        setObservers()

        return binding.root
    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver { navigateTo(it) })
    }

    private fun updateUI(model:  RescuerLoginViewModel.UiModel){
        when(model){
            is RescuerLoginViewModel.UiModel.Loading -> binding.rescuerLoginProgress.visibility = if(model.show) View.VISIBLE else View.GONE
            is RescuerLoginViewModel.UiModel.RescuerError -> showRescuerError(model.rescuerView)
            is RescuerLoginViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
        }
    }

    private fun navigateTo (model: RescuerLoginViewModel.UiNavigation){
        when(model){
            is RescuerLoginViewModel.UiNavigation.GoHome -> navigateToMainActivity()
            is RescuerLoginViewModel.UiNavigation.GoRegister -> navigateToRescuerRegistration()
        }
    }

    private fun setListener() {
        binding.rescuerLoginBtn.setOnClickListener{
            onClickLogin()

        }
        binding.recuerRegisterBtn.setOnClickListener(){
            onClickRegister()
        }
    }

    private fun showErrorAdvice(message: Int){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message).setCancelable(false).setPositiveButton(R.string.action_accept) { dialog, _->
                dialog.dismiss()

            }
        builder.create().show()
    }

    private fun showRescuerError(userView: UserView){
        with(binding){
            rescuerEmail.error = if (userView.email.valid) null else getString(userView.email.messageResourceId)
            rescuerPassword.error = if (userView.password.valid) null else getString(userView.password.messageResourceId)
        }
    }

    private fun navigateToMainActivity(){
        val action = RescuerLoginFragmentDirections.actionRescuerLoginFragmentToMainRescuerActivity()
        view?.findNavController()?.navigate(action)
        activity?.finish()
    }

    private fun navigateToRescuerRegistration() {
        val action = RescuerLoginFragmentDirections.actionRescuerLoginFragmentToRescuerRegistrationFragment()
        view?.findNavController()?.navigate(action)
    }

    private fun onClickLogin(){
        with(binding){
            val user = UserView(
                FieldView(rescuerEmail.text.toString()),
                FieldView(rescuerPassword.text.toString())
            )
            viewModel.onLoginRescuer(user)
        }
    }

    private fun onClickRegister(){
        viewModel.onRegisterRescuer()
    }

}


