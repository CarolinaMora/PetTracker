package com.teammovil.pettracker.ui.petregistration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.databinding.FragmentPetRegistrationBinding
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.domain.PetStatus
import com.teammovil.pettracker.domain.PetType
import com.teammovil.pettracker.domain.Vaccine
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.dewormings.DewormingsListFragment
import com.teammovil.pettracker.ui.vaccines.VaccinesListFragment
import com.teammovil.pettracker.ui.views.DatePickerFragment
import java.util.*


class PetRegistrationFragment : Fragment(), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentPetRegistrationBinding
    private lateinit var viewModel : PetRegistrationViewModel
    private var photoTaker : PhotoTaker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetRegistrationBinding.inflate(inflater)

        viewModel = ViewModelProvider(
                        this,
                        PetRegistrationViewModelFactory(PetRepository(PetFakeExternalDataAccess()))
                )[PetRegistrationViewModel::class.java]

        photoTaker = PhotoTaker(requireContext())
        photoTaker?.fragment = this
        setViews()
        setListeners()
        setObservers()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoTaker?.onActivityResult(requestCode, resultCode, data, binding.petRegistrationMainPhoto)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        photoTaker?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun saveDate(date: String, idCaller: Int) {
        when (idCaller){
            binding.petRegistrationBirthDate.id -> binding.petRegistrationBirthDate.setText(date)
            binding.petRegistrationRescueDate.id -> binding.petRegistrationRescueDate.setText(date)
        }
    }

    private fun setObservers (){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver{ navigateUp() })
    }

    private fun updateUI(model: PetRegistrationViewModel.UiModel) {
        binding.petRegistrationProgress.visibility = if (model is PetRegistrationViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when(model){
            is PetRegistrationViewModel.UiModel.PetError -> showPetError(model.petView)
            is PetRegistrationViewModel.UiModel.ErrorAdvice -> showErrorAdvice(model.message)
            is PetRegistrationViewModel.UiModel.SuccessAdvice -> showSuccessAdvice(model.message)
        }
    }

    private fun setListeners (){
        //Register button
        binding.petRegistrationRegisterAction.setOnClickListener{
            onClickRegister()
        }

        //Image button
        binding.petRegistrationMainPhoto.setOnClickListener {
            onTakePhoto()
        }

        //Dates
        binding.petRegistrationBirthDate.setOnClickListener{
            onClickDate(it.id)
        }

        binding.petRegistrationRescueDate.setOnClickListener {
            onClickDate(it.id)
        }
    }

    private fun setViews (){
        //Type selection
        val arrayType = listOf(getString(R.string.prompt_select_option)) + PetType.values().map{it.name}
        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayType
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petRegistrationType.adapter = adapterType

        //Gender selection
        val arrayGender = listOf(getString(R.string.prompt_select_option)) + GenderType.values().map{it.name}
        val adapterGender: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayGender
        )
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petRegistrationGender.adapter = adapterGender

    }

    private fun showPetError (petView: PetView){
        binding.petRegistrationName.error = if(petView.name.valid) null else getString(petView.name.messageResourceId)
        binding.petRegistrationDescription.error = if(petView.description.valid) null else getString(petView.description.messageResourceId)
        binding.petRegistrationRace.error = if(petView.race.valid) null else getString(petView.race.messageResourceId)
        binding.petRegistrationBirthDate.error = if(petView.approximateDateOfBirth.valid) null else getString(petView.approximateDateOfBirth.messageResourceId)
        binding.petRegistrationRescueDate.error = if(petView.rescueDate.valid) null else getString(petView.rescueDate.messageResourceId)
        binding.petRegistrationGenderError.text = if(petView.gender.valid) "" else getString(petView.gender.messageResourceId)
        binding.petRegistrationTypeError.text = if(petView.petType.valid) "" else getString(petView.petType.messageResourceId)
        binding.petRegistrationPhotoError.text = if(petView.mainPhoto.valid) "" else getString(petView.mainPhoto.messageResourceId)
    }

    private fun navigateUp (){
        view?.findNavController()?.navigateUp()
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

    private fun onTakePhoto (){
        photoTaker?.dispatchTakePictureIntent()
    }


    private fun onClickDate (idCaller: Int){
        val newFragment = DatePickerFragment(idCaller)
        newFragment.listener = this
        newFragment.show(childFragmentManager, "datePicker")
    }

    private fun onClickRegister (){

        //Vaccines
        var vaccinesList : List<Vaccine>? = null
        val vaccinesFragment = childFragmentManager.findFragmentById(R.id.pet_registration_vaccines)
        if(vaccinesFragment is VaccinesListFragment){
            vaccinesList = Mapper.mapVaccineList(vaccinesFragment.getVaccinesList())
        }

        //Dewormings
        var dewormingsList : List<Date>? = null
        val dewormingsFragment = childFragmentManager.findFragmentById(R.id.pet_registration_dewormings)
        if(dewormingsFragment is DewormingsListFragment){
            dewormingsList = Mapper.mapDewormingList(dewormingsFragment.getDewormingsList())
        }

        with(binding) {
            val pet = PetView(
                    null,
                    FieldView(petRegistrationName.text.toString()),
                    SelectFieldView(petRegistrationGender.selectedItem.toString(), petRegistrationGender.selectedItemPosition),
                    FieldView(petRegistrationRace.text.toString()),
                    FieldView(petRegistrationDescription.text.toString()),
                    FieldView(petRegistrationBirthDate.text.toString()),
                    FieldView(petRegistrationRescueDate.text.toString()),
                    SelectFieldView(petRegistrationType.selectedItem.toString(), petRegistrationType.selectedItemPosition),
                    FieldView(petRegistrationSterilized.isChecked),
                    FieldView(vaccinesList),
                    FieldView(dewormingsList),
                    FieldView(photoTaker?.currentPhotoPath),
                    FieldView(PetStatus.RESCUED),
                    FieldView(null)
            )
            viewModel.onSavePet(pet)
        }
    }


}