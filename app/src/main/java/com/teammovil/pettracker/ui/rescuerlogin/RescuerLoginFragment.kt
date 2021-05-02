package com.teammovil.pettracker.ui.rescuerlogin

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
import com.teammovil.pettracker.data.database.dataaccess.RescuerStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.databinding.FragmentRescuerLoginBinding
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.common.UserView


class RescuerLoginFragment : Fragment() {

    private lateinit var viewModel: RescuerLoginViewModel
    private lateinit var binding: FragmentRescuerLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRescuerLoginBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, RescuerLoginViewModelFactory(
            RescuerRepository(
                RescuerFakeExternalDataAccess(),
                RescuerStorageDataAccessDataBaseImpl(requireContext())
            )
        )
        )[RescuerLoginViewModel::class.java]

        setListener()
        setObservers()

        return binding.root
    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, Observer { navigateToMainActivity() })
    }

    private fun updateUI(model:  RescuerLoginViewModel.UiModel){

        when(model){
            is RescuerLoginViewModel.UiModel.LoginError -> showRescuerError(model.userView)
            is RescuerLoginViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
        }
    }

    private fun setListener() {
        binding.rescuerLoginBtn.setOnClickListener{
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

    private fun showRescuerError(userView: UserView){
        binding.rescuerEmail.error = if (userView.email.valid) null else getString(userView.email.messageResourceId)
        binding.rescuerPassword.error = if (userView.password.valid) null else getString(userView.password.messageResourceId)
    }

    private fun navigateToMainActivity(){
        view?.findNavController()?.navigate(R.id.action_rescuerLoginFragment_to_mainActivity)
    }

    private fun onClickLogin(){
        with(binding){
            val user = UserView(
                FieldView(rescuerEmail.text.toString()),
                FieldView(rescuerPassword.text.toString())
            )
            viewModel.onLoginAdopter(user)
        }
    }

}


