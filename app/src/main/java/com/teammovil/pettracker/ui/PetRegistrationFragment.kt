package com.teammovil.pettracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentPetRegistrationBinding
import com.teammovil.pettracker.getStringFromDate
import com.teammovil.pettracker.ui.vaccines.VaccineView
import com.teammovil.pettracker.ui.vaccines.VaccinesListFragment
import java.util.*

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
        val fragment = childFragmentManager.findFragmentById(R.id.pet_registration_vaccines)
        if(fragment is VaccinesListFragment){
            fragment.setVaccinesList(listOf(
                VaccineView(name = "puppy 1", applicationDate = getStringFromDate(Date())!!)
            ))
        }
    }

    fun onClickRegister (){

    }

}