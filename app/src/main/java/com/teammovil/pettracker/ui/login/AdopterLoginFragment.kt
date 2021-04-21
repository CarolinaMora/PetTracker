package com.teammovil.pettracker.ui.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentAdopterLoginBinding
import kotlinx.coroutines.launch


class AdopterLoginFragment : Fragment(R.layout.fragment_adopter_login) {
    // TODO: Rename and change types of parameters

    private lateinit var viewModel: AdopterLoginViewModel
    private lateinit var binding: FragmentAdopterLoginBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentAdopterLoginBinding.bind(view)

        viewModel = ViewModelProvider(this, AdopterLoginViewModelFactory(
            AdopterRepository(
                FakeAdopterExternalDataAccess(),
                FakeAdopterStorageDataAccess()
            ))
        )[AdopterLoginViewModel::class.java]

        setListener()
        setObservers()


//        binding.adopterLoginBtn.setOnClickListener{
//            val repository = AdopterRepository(FakeAdopterExternalDataAccess(), FakeAdopterStorageDataAccess())
//            viewLifecycleOwner.lifecycleScope.launch {
//                val user = ""
//                val password = ""
//                val result = repository.login(user, password)
//                if (result)  {
//                    val intent = Intent(activity, MainActivity::class.java)
//                    startActivity(intent)
//
//                }
//            }
//
//        }



    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, Observer { navigateUp() })
    }

    private fun updateUI(model:  AdopterLoginViewModel.UiModel){

        when(model){
            is AdopterLoginViewModel.UiModel.LoginError -> showAdopterError(model.adopterView)
            is AdopterLoginViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
            is AdopterLoginViewModel.UiModel.SuccessNotification -> showSuccessAdvice(model.message)
        }
    }

    private fun setListener() {
        binding.adopterLoginBtn.setOnClickListener{
            onClickLogin()

        }
        binding.BtnCancel.setOnClickListener(){

        }
    }

    private fun showSuccessAdvice (message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.email_adopter_registration) { dialog, _ ->
                dialog.dismiss()
                viewModel.onClickOkAdvice()
            }
        builder.create().show()
    }
    private fun showErrorAdvice(message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message).setCancelable(false).setPositiveButton(R.string.email_adopter_registration) { dialog, _->
                dialog.dismiss()

            }
        builder.create().show()
    }
    private fun showAdopterError(adopterView: AdopterView){
        binding.emailAdopted.error = if (adopterView.email.valid) null else adopterView.email.message
        binding.passAdopted.error = if (adopterView.password.valid) null else adopterView.password.message
    }
    private fun navigateUp(){
        view?.findNavController()?.navigateUp()
    }

    private fun onClickLogin(){
        with(binding){
            val adopter = AdopterView("",
                FieldView(emailAdopted.text.toString()),
                FieldView(passAdopted.text.toString())
            )
            viewModel.onLoginAdopter(adopter)
        }
    }

}


