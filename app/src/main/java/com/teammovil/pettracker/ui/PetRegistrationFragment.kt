package com.teammovil.pettracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.teammovil.pettracker.databinding.FragmentPetRegistrationBinding
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.domain.PetType


class PetRegistrationFragment : Fragment() {

    private lateinit var binding: FragmentPetRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPetRegistrationBinding.inflate(inflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews (){
        //Type selection
        val arrayType = listOf("Seleccione la especie") + PetType.values().map{it.type}
        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayType
        )
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petRegistrationType.adapter = adapterType

        //Gender selection
        val arrayGender = listOf("Seleccione el género") + GenderType.values().map{it.gender}
        val adapterGender: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayGender
        )
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.petRegistrationGender.adapter = adapterGender
    }

    fun onClickRegister (){

    }

}