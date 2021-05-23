package com.teammovil.pettracker.ui.adopterregistration

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.domain.GenderType
import com.teammovil.pettracker.data.database.dataaccess.AdopterStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.services.AdopterExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentAdopterRegistrationBinding
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.views.DatePickerFragment
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdopterRegistrationFragment : Fragment(R.layout.fragment_adopter_registration), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentAdopterRegistrationBinding
    private val viewModel: AdopterRegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAdopterRegistrationBinding.bind(view)
        setViews()
        setListeners()
        setObservers()

    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver{ navigateUp() })
    }

    private fun updateUI(model: AdopterRegistrationViewModel.UiModel){

        when(model){
            is AdopterRegistrationViewModel.UiModel.AdopterError -> showAdopterError(model.adopterView)
            is AdopterRegistrationViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.message)
            is AdopterRegistrationViewModel.UiModel.SuccessNotification -> showSuccessAdvice(model.message)
        }
    }

    private fun setViews(){
        //Gender selection
        val arrayGender = listOf("Seleccione el género") + com.teammovil.domain.GenderType.values().map{it.name}
        val adapterGender: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayGender
        )
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.adopterRegistrationGender.adapter = adapterGender
    }

    private fun setListeners(){
        //BirthDate
        binding.adopterRegistrationBirthDate.setOnClickListener{
            onClickDate(it.id)
        }

        //Register adopter button
        binding.btnAdopterRegister.setOnClickListener{
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
            binding.adopterRegistrationBirthDate.id -> binding.adopterRegistrationBirthDate.setText(date)
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

    private fun showAdopterError(adopterView: AdopterView){
        binding.adopterRegistrationName.error = if (adopterView.name.valid) null else getString(adopterView.name.messageResourceId)
        binding.adopterRegistrationLastName.error = if (adopterView.firstLastName.valid) null else getString(adopterView.firstLastName.messageResourceId)
        binding.adopterRegistrationSecondSurname.error = if (adopterView.secondLastName.valid) null else getString(adopterView.secondLastName.messageResourceId)
        binding.adopterRegistrationGenderError.text = if (adopterView.genderType.valid) GenderType.UNKNOWN.name else getString(adopterView.genderType.messageResourceId)
        binding.adopterRegistrationBirthDate.error = if (adopterView.birthDay.valid) null else getString(adopterView.birthDay.messageResourceId)
        binding.adopterRegistrationEmail.error = if (adopterView.email.valid) null else getString(adopterView.email.messageResourceId)
        binding.adopterRegistrationPassword.error = if (adopterView.password.valid) null else getString(adopterView.password.messageResourceId)
        binding.adopterRegistrationPhone.error = if (adopterView.phone.valid) null else getString(adopterView.phone.messageResourceId)
        binding.adopterRegistrationAddress.error = if (adopterView.address.valid) null else getString(adopterView.address.messageResourceId)
    }

    private fun navigateUp (){
        view?.findNavController()?.navigateUp()
    }

    private fun onClickRegister(){
        with(binding){
            val adopterView = AdopterView(
                null,
                FieldView(adopterRegistrationName.text.toString()),
                FieldView(adopterRegistrationLastName.text.toString()),
                FieldView(adopterRegistrationSecondSurname.text.toString()),
                FieldView(adopterRegistrationGender.selectedItem.toString()),
                FieldView(adopterRegistrationBirthDate.text.toString()),
                FieldView(adopterRegistrationEmail.text.toString()),
                FieldView(adopterRegistrationPassword.text.toString()),
                FieldView(adopterRegistrationPhone.text.toString()),
                FieldView(adopterRegistrationAddress.text.toString())
            )
            viewModel.onSaveAdopter(adopterView)
        }
    }
}