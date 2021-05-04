package com.teammovil.pettracker.ui.rescuerregistration

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.database.dataaccess.RescuerStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.services.RescuerExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentRescuerRegistrationBinding
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.views.DatePickerFragment


class RescuerRegistrationFragment : Fragment(R.layout.fragment_rescuer_registration), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentRescuerRegistrationBinding
    private lateinit var viewModel: RescuerRegistrationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentRescuerRegistrationBinding.bind(view)
        viewModel = ViewModelProvider(
            this,
            RescuerRegistrationViewModelFactory(
                RescuerRepository(
                    RescuerExternalDataAccessServiceImpl(),
                    RescuerStorageDataAccessDataBaseImpl(requireContext())
                )
            )
        )[RescuerRegistrationViewModel::class.java]
        setListeners()
        setObservers()

    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver{ navigateUp() })
    }

    private fun updateUI(model: RescuerRegistrationViewModel.UiModel){

        when(model){
            is RescuerRegistrationViewModel.UiModel.RescuerError -> showRescuerError(model.rescuerView)
            is RescuerRegistrationViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
            is RescuerRegistrationViewModel.UiModel.SuccessNotification -> showSuccessAdvice(model.message)
        }
    }

    private fun setListeners(){
        //BirthDate
        binding.rescuerRegistrationStartDate.setOnClickListener{
            onClickDate(it.id)
        }

        //Register rescuer button
        binding.btnRescuerRegister.setOnClickListener{
            onClickRegister()
        }
    }

    private fun onClickDate (idCaller: Int){
        val newFragment = DatePickerFragment(idCaller)
        newFragment.listener = this
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun saveDate(date: String, idCaller: Int) {
        when (idCaller){
            binding.rescuerRegistrationStartDate.id -> binding.rescuerRegistrationStartDate.setText(date)
        }
    }

    private fun showSuccessAdvice (message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
                viewModel.onClickOkAdvice()
            }
        builder.create().show()
    }

    private fun showErrorAdvice (message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun showRescuerError(rescuerView: RescuerView){
        binding.rescuerRegistrationName.error = if (rescuerView.name.valid) null else rescuerView.name.message
        binding.rescuerRegistrationStartDate.error = if (rescuerView.activityStartDate.valid) null else rescuerView.activityStartDate.message
        binding.rescuerRegistrationEmail.error = if (rescuerView.email.valid) null else rescuerView.email.message
        binding.rescuerRegistrationPassword.error = if (rescuerView.password.valid) null else rescuerView.password.message
        binding.rescuerRegistrationPhone.error = if (rescuerView.phone.valid) null else rescuerView.phone.message
        binding.rescuerRegistrationAddress.error = if (rescuerView.address.valid) null else rescuerView.address.message
        binding.rescuerRegistrationDescription.error = if (rescuerView.descripion.valid) null else rescuerView.descripion.message
    }

    private fun navigateUp (){
        view?.findNavController()?.navigateUp()
    }

    private fun onClickRegister(){
        with(binding){
            val rescuerView = RescuerView(
                null,
                FieldView(rescuerRegistrationName.text.toString()),
                FieldView(rescuerRegistrationDescription.text.toString()),
                FieldView(rescuerRegistrationAddress.text.toString()),
                FieldView(rescuerRegistrationEmail.text.toString()),
                FieldView(rescuerRegistrationPassword.text.toString()),
                FieldView(rescuerRegistrationPhone.text.toString()),
                FieldView(rescuerRegistrationStartDate.text.toString())
            )
            viewModel.onSaveRescuer(rescuerView)
        }
    }
}