package com.teammovil.pettracker.ui.adopterregistration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentAdopterRegistrationBinding
import com.teammovil.pettracker.domain.GenderType
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
                    FakeAdopterStorageDataAccess()
                )
            )
        )[AdopterRegistrationViewModel::class.java]
        setViews()
        setListeners()

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
        //BirtDate
        binding.adopterRegistrationBirthDate.setOnClickListener{
            onClickDate(it.id)
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
}