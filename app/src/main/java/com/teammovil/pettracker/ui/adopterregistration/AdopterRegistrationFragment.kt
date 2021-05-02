package com.teammovil.pettracker.ui.adopterregistration

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.database.dataaccess.AdopterStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.databinding.FragmentAdopterRegistrationBinding
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.views.DatePickerFragment


class AdopterRegistrationFragment : Fragment(R.layout.fragment_adopter_registration), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentAdopterRegistrationBinding
    private lateinit var viewModel: AdopterRegistrationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentAdopterRegistrationBinding.bind(view)
        viewModel = ViewModelProvider(
            this,
            AdopterRegistrationViewModelFactory(
                AdopterRepository(
                    FakeAdopterExternalDataAccess(),
                    AdopterStorageDataAccessDataBaseImpl(requireContext())
                )
            )
        )[AdopterRegistrationViewModel::class.java]
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
        val arrayGender = listOf("Seleccione el género") + GenderType.values().map{it.name}
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
        binding.adopterRegistrationName.error = if (adopterView.name.valid) null else adopterView.name.message
        binding.adopterRegistrationLastName.error = if (adopterView.firstLastName.valid) null else adopterView.firstLastName.message
        binding.adopterRegistrationSecondSurname.error = if (adopterView.secondLastName.valid) null else adopterView.secondLastName.message
        binding.adopterRegistrationGenderError.text = if (adopterView.genderType.valid) "" else adopterView.genderType.message
        binding.adopterRegistrationBirthDate.error = if (adopterView.birthDay.valid) null else adopterView.birthDay.message
        binding.adopterRegistrationEmail.error = if (adopterView.email.valid) null else adopterView.email.message
        binding.adopterRegistrationPassword.error = if (adopterView.password.valid) null else adopterView.password.message
        binding.adopterRegistrationPhone.error = if (adopterView.phone.valid) null else adopterView.phone.message
        binding.adopterRegistrationAddress.error = if (adopterView.address.valid) null else adopterView.address.message
    }

    private fun navigateUp (){
        view?.findNavController()?.navigateUp()
    }

    private fun onClickRegister(){
        with(binding){
            val adopter = AdopterView(
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
            viewModel.onSaveAdopter(adopter)
        }
    }
}