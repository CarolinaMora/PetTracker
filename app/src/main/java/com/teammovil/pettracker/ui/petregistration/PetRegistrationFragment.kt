package com.teammovil.pettracker.ui.petregistration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentPetRegistrationBinding
import com.teammovil.pettracker.domain.*
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.dewormings.DewormingsListFragment
import com.teammovil.pettracker.ui.vaccines.VaccinesListFragment
import com.teammovil.pettracker.ui.views.DatePickerFragment
import kotlinx.coroutines.launch
import java.util.*


class PetRegistrationFragment : Fragment(), DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentPetRegistrationBinding
    private var photoTaker : PhotoTaker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetRegistrationBinding.inflate(inflater)

        photoTaker = PhotoTaker(requireContext())
        photoTaker?.fragment = this
        setViews()
        setListeners()

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
        val arrayType = listOf("Seleccione la especie") + PetType.values().map{it.name}
        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayType
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petRegistrationType.adapter = adapterType

        //Gender selection
        val arrayGender = listOf("Seleccione el género") + GenderType.values().map{it.name}
        val adapterGender: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayGender
        )
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petRegistrationGender.adapter = adapterGender

    }

    private fun onTakePhoto (){
        activity?.let {
            photoTaker?.dispatchTakePictureIntent()
        }
    }


    private fun onClickDate (idCaller: Int){
        val newFragment = DatePickerFragment(idCaller)
        newFragment.listener = this
        newFragment.show(childFragmentManager, "datePicker")
    }

    private fun onClickRegister (){

        //Vaccines
        var vaccinesList = listOf<Vaccine>()
        val vaccinesFragment = childFragmentManager.findFragmentById(R.id.pet_registration_vaccines)
        if(vaccinesFragment is VaccinesListFragment){
            vaccinesList = Mapper.mapVaccineList(vaccinesFragment.getVaccinesList())
        }

        //Dewormings
        var dewormingsList = listOf<Date>()
        val dewormingsFragment = childFragmentManager.findFragmentById(R.id.pet_registration_dewormings)
        if(dewormingsFragment is DewormingsListFragment){
            dewormingsList = Mapper.mapDewormingList(dewormingsFragment.getDewormingsList())
        }

        if(validateInput()) {
            with(binding) {
                val pet = Pet(
                    "0",
                    petRegistrationName.text.toString(),
                    GenderType.valueOf(petRegistrationGender.selectedItem.toString()),
                    petRegistrationRace.text.toString(),
                    petRegistrationDescription.text.toString(),
                    getDateFromString(petRegistrationBirthDate.text.toString())?.let{it}?: Date(),
                    getDateFromString(petRegistrationRescueDate.text.toString())?.let{it}?: Date(),
                    PetType.valueOf(petRegistrationType.selectedItem.toString()),
                    petRegistrationSterilized.isChecked,
                    vaccinesList,
                    dewormingsList,
                    photoTaker?.currentPhotoPath?.let { it } ?: "",
                    PetStatus.RESCUED.ordinal,
                        listOf()//TODO: Evidencias
                )
                savePet(pet)
            }
        }
        else{
            Toast.makeText(requireContext(), "Hubo un error favor de revisar", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateInput (): Boolean = binding.petRegistrationGender.selectedItemPosition != 0 &&
                binding.petRegistrationType.selectedItemPosition != 0 &&
            photoTaker?.currentPhotoPath != null


    private fun savePet (pet: Pet){
        val repository = PetRepository(PetFakeExternalDataAccess())
        viewLifecycleOwner.lifecycleScope.launch {
            val result = repository.registerPet(pet)
            if(result) Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_LONG).show()
        }
    }

}