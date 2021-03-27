package com.teammovil.pettracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentPetRegistrationBinding
import com.teammovil.pettracker.getStringFromDate
import com.teammovil.pettracker.ui.dewormings.DewormingView
import com.teammovil.pettracker.ui.dewormings.DewormingsListFragment
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
        setViews()
    }

    private fun setViews (){
        //Vaccines
        val vaccinesFragment = childFragmentManager.findFragmentById(R.id.pet_registration_vaccines)
        if(vaccinesFragment is VaccinesListFragment){
            vaccinesFragment.setVaccinesList(listOf(
                VaccineView(name = "puppy 1", applicationDate = getStringFromDate(Date())!!)
            ))
        }

        //Dewormings
        val dewormingsFragment = childFragmentManager.findFragmentById(R.id.pet_registration_dewormings)
        if(dewormingsFragment is DewormingsListFragment){
            dewormingsFragment.setDewormingsList(listOf(
                DewormingView(name = "Tableta 1", applicationDate = getStringFromDate(Date())!!)
            ))
        }
    }

    fun onClickRegister (){

    }

}