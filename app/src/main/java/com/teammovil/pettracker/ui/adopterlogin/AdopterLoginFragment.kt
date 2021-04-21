package com.teammovil.pettracker.ui.adopterlogin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentAdopterLoginBinding
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.common.UserView


class AdopterLoginFragment : Fragment() {

    private lateinit var viewModel: AdopterLoginViewModel
    private lateinit var binding: FragmentAdopterLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdopterLoginBinding.inflate(inflater)

        viewModel = ViewModelProvider(this, AdopterLoginViewModelFactory(
            AdopterRepository(
                FakeAdopterExternalDataAccess(),
                FakeAdopterStorageDataAccess()
            ))
        )[AdopterLoginViewModel::class.java]

        setListener()
        setObservers()

        return binding.root
    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, Observer { navigateToMainActivity() })
    }

    private fun updateUI(model:  AdopterLoginViewModel.UiModel){

        when(model){
            is AdopterLoginViewModel.UiModel.LoginError -> showAdopterError(model.userView)
            is AdopterLoginViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
        }
    }

    private fun setListener() {
        binding.adopterLoginBtn.setOnClickListener{
            onClickLogin()

        }
        binding.BtnCancel.setOnClickListener(){

        }
    }

    private fun showErrorAdvice(message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message).setCancelable(false).setPositiveButton(R.string.action_accept) { dialog, _->
                dialog.dismiss()

            }
        builder.create().show()
    }

    private fun showAdopterError(userView: UserView){
        binding.emailAdopted.error = if (userView.email.valid) null else userView.email.message
        binding.passAdopted.error = if (userView.password.valid) null else userView.password.message
    }

    private fun navigateToMainActivity(){
        view?.findNavController()?.navigate(R.id.action_adopterLoginFragment_to_mainActivity)
    }

    private fun onClickLogin(){
        with(binding){
            val user = UserView(
                FieldView(emailAdopted.text.toString()),
                FieldView(passAdopted.text.toString())
            )
            viewModel.onLoginAdopter(user)
        }
    }

}


