package com.teammovil.pettracker.ui.editregisterpet

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
import com.teammovil.pettracker.data.database.dataaccess.RescuerStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.data.services.RescuerExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentPetRegistrationBinding
import com.teammovil.pettracker.domain.*
import com.teammovil.pettracker.ui.common.*
import com.teammovil.pettracker.ui.dewormings.DewormingsListFragment
import com.teammovil.pettracker.ui.petdetail.ARG_PET_ID
import com.teammovil.pettracker.ui.vaccines.VaccinesListFragment
import com.teammovil.pettracker.ui.views.DatePickerFragment


class EditRegisterPetFragment : Fragment(), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentPetRegistrationBinding
    private lateinit var viewModel : EditRegisterPetViewModel
    private var photoTaker : PhotoTaker? = null
    private var petId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        petId = arguments?.getString(ARG_PET_ID)

        viewModel = ViewModelProvider(
                        this,
                        EditRegisterPetViewModelFactory(
                            PetRepository(PetExternalDataAccessServiceImpl()),
                            RescuerRepository(RescuerExternalDataAccessServiceImpl(), RescuerStorageDataAccessDataBaseImpl(requireContext()))
                        )
                )[EditRegisterPetViewModel::class.java]

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
        viewModel.onStartView(petId)
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
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver{ updateUI(it) })
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

    private fun updateUI(model: EditRegisterPetViewModel.UiModel) {
        when(model){
            is EditRegisterPetViewModel.UiModel.Loading -> binding.petRegistrationProgress.visibility = if (model.show) View.VISIBLE else View.GONE
            is EditRegisterPetViewModel.UiModel.ErrorAdvice -> showErrorAdvice(model.message)
            is EditRegisterPetViewModel.UiModel.SuccessAdvice -> showSuccessAdvice()
        }
    }

    private fun updateUI (navigation: EditRegisterPetViewModel.UiEvents){
        when(navigation){
            is EditRegisterPetViewModel.UiEvents.GoToAssignAdopter -> navigateToAssignAdopter()
            is EditRegisterPetViewModel.UiEvents.GoToSendEvidence -> navigateToSendEvidence()
            is EditRegisterPetViewModel.UiEvents.GoBack -> navigateUp()
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

        binding.btAssignAdopter.setOnClickListener {
            onClickAssignAdopter()
        }

        binding.btSendEvidence.setOnClickListener {
            onClickSendEvidence()
        }
    }

    private fun onClickSendEvidence() {
        petId?.let{
            viewModel.onSendEvidence(it)
        }
    }

    private fun onClickAssignAdopter() {
        petId?.let{
            viewModel.onAssignAdopter(it)
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

        //Buttons
        if(petId.isNullOrEmpty()){
            binding.btAssignAdopter.visibility = View.GONE
            binding.btSendEvidence.visibility = View.GONE
        }

    }

    private fun navigateUp (){
        view?.findNavController()?.navigateUp()
    }

    private fun navigateToSendEvidence (){
        petId?.let {
            val action = EditRegisterPetFragmentDirections.actionAdopterRegistrationFragmentToSendEvidenceFragment(it)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun navigateToAssignAdopter (){
        petId?.let {
            val action = EditRegisterPetFragmentDirections.actionAdopterRegistrationFragmentToAssignAdopterToPetFragment(it)
            view?.findNavController()?.navigate(action)
        }
    }

    private fun showSuccessAdvice (){
        val builder = AlertDialog.Builder(requireContext())
                .setMessage(R.string.alert_message_pet_saved)
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
        var dewormingsList : List<Deworming>? = null
        val dewormingsFragment = childFragmentManager.findFragmentById(R.id.pet_registration_dewormings)
        if(dewormingsFragment is DewormingsListFragment){
            dewormingsList = Mapper.mapDewormingList(dewormingsFragment.getDewormingsList())
        }

        with(binding) {
            val pet = PetView(
                petId ?: "",
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
                FieldView(
                    if(petRegistrationStatus.text.toString().isEmpty()) PetStatus.RESCUED
                    else  PetStatus.valueOf(petRegistrationStatus.text.toString())),
                FieldView(null)
            )
            return pet
        }
    }


}