package com.teammovil.pettracker.ui.editpet

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
import com.teammovil.pettracker.ui.common.*
import com.teammovil.pettracker.ui.dewormings.DewormingsListFragment
import com.teammovil.pettracker.ui.vaccines.VaccinesListFragment
import com.teammovil.pettracker.ui.views.DatePickerFragment
import java.util.*


class EditPetFragment : Fragment(), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentPetRegistrationBinding
    private lateinit var viewModel : EditPetViewModel
    private var photoTaker : PhotoTaker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(
                        this,
                        EditPetViewModelFactory(PetRepository(PetFakeExternalDataAccess()))
                )[EditPetViewModel::class.java]

        binding = FragmentPetRegistrationBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        photoTaker =
            PhotoTaker(requireContext())
        photoTaker?.fragment = this
        setViews()
        setListeners()
        setObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onStartView("1")
    }

    override fun onStop() {
        super.onStop()
        val pet = savePet()
        viewModel.onSavePetLocal(pet)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoTaker?.onActivityResult(requestCode, resultCode, data, binding.petRegistrationMainPhoto)
        viewModel.onSavePhotoUrl(photoTaker?.currentPhotoPath)
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
        viewModel.petView.observe(viewLifecycleOwner, Observer { updateRestPet(it) })
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver{ navigateUp() })
    }

    private fun updateRestPet(petView: PetView?) {
        petView?.let{ pet ->
            pet.vaccines.value?.let{ list ->
                val vaccinesFragment = childFragmentManager.findFragmentById(R.id.pet_registration_vaccines)
                if(vaccinesFragment is VaccinesListFragment){
                    vaccinesFragment.setVaccinesList(Mapper.mapVaccineViewList(list))
                }
            }

            pet.dewormings.value?.let{ list ->
                val dewormingsFragment = childFragmentManager.findFragmentById(R.id.pet_registration_dewormings)
                if(dewormingsFragment is DewormingsListFragment){
                    dewormingsFragment.setDewormingsList(Mapper.mapDewormingViewList(list))
                }
            }

            pet.mainPhoto.value?.let{url ->
                photoTaker?.currentPhotoPath = url
            }

        }
    }

    private fun updateUI(model: EditPetViewModel.UiModel) {
        when(model){
            is EditPetViewModel.UiModel.Loading -> binding.petRegistrationProgress.visibility = if (model.show) View.VISIBLE else View.GONE
            is EditPetViewModel.UiModel.ErrorAdvice -> showErrorAdvice(model.message)
            is EditPetViewModel.UiModel.SuccessAdvice -> showSuccessAdvice(model.message)
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
        val pet = savePet()
        viewModel.onSavePet(pet)
    }

    private fun savePet (): PetView{
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
                "1",
                FieldView(petRegistrationName.text.toString()),
                SelectFieldView(
                    petRegistrationGender.selectedItem.toString(),
                    petRegistrationGender.selectedItemPosition
                ),
                FieldView(petRegistrationRace.text.toString()),
                FieldView(petRegistrationDescription.text.toString()),
                FieldView(petRegistrationBirthDate.text.toString()),
                FieldView(petRegistrationRescueDate.text.toString()),
                SelectFieldView(
                    petRegistrationType.selectedItem.toString(),
                    petRegistrationType.selectedItemPosition
                ),
                FieldView(petRegistrationSterilized.isChecked),
                FieldView(vaccinesList),
                FieldView(dewormingsList),
                FieldView(photoTaker?.currentPhotoPath),
                FieldView(PetStatus.RESCUED),
                FieldView(null)
            )
            return pet
        }
    }


}