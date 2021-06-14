package com.teammovil.pettracker.ui.adopterlogin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentAdopterLoginBinding
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.common.UserView

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdopterLoginFragment : Fragment() {

    private val viewModel: AdopterLoginViewModel by viewModels()
    private lateinit var binding: FragmentAdopterLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdopterLoginBinding.inflate(inflater)

        setListener()
        setObservers()

        return binding.root
    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner,  { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver { navigateTo(it) })
    }

    private fun updateUI(model:  AdopterLoginViewModel.UiModel){
        when(model){
            is AdopterLoginViewModel.UiModel.Loading -> binding.adopterLoginProgress.visibility = if(model.show) View.VISIBLE else View.GONE
            is AdopterLoginViewModel.UiModel.LoginError -> showAdopterError(model.adopterView)
            is AdopterLoginViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
        }
    }

    private fun navigateTo (model: AdopterLoginViewModel.UiNavigation) = when(model){
        is AdopterLoginViewModel.UiNavigation.GoHome -> navigateToMainActivity()
        is AdopterLoginViewModel.UiNavigation.GoRegister -> navigateToAdopterRegistration()
    }

    private fun setListener() {
        binding.adopterLoginBtn.setOnClickListener{
            onClickLogin()

        }
        binding.BtnCancel.setOnClickListener(){
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

    private fun showAdopterError(userView: UserView){
        with(binding){
            emailAdopted.error = if (userView.email.valid) null else getString(userView.email.messageResourceId)
            passAdopted.error = if (userView.password.valid) null else getString(userView.password.messageResourceId)
        }
    }

    private fun navigateToMainActivity(){
        val action = AdopterLoginFragmentDirections.actionAdopterLoginFragmentToMainActivity()
        view?.findNavController()?.navigate(action)
        activity?.finish()
    }

    private fun navigateToAdopterRegistration (){
        val action = AdopterLoginFragmentDirections.actionAdopterLoginFragmentToAdopterRegistrationFragment2()
        view?.findNavController()?.navigate(action)
    }

    private fun onClickLogin(){
        with(binding){
            val user = UserView(
                FieldView(emailAdopted.text.toString()),
                FieldView(emailAdopted.text.toString())
            )
            viewModel.onLoginAdopter(user)


        }
    }

    private fun onClickRegister(){
        viewModel.onRegisterAdopter()
    }
}


