package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeStorageDataAccess
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentRegisteredPetsBinding
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsAdapter
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModel
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterPetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterPetsFragment : Fragment() {

    lateinit var binding: FragmentRegisteredPetsBinding
    lateinit var petsAdapter: RegisteredPetsAdapter
    private lateinit var viewModel: RegisteredPetsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisteredPetsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val petFake = PetExternalDataAccessServiceImpl()
        val rescuerFake = RescuerFakeExternalDataAccess()
        val rescuerStorage = RescuerFakeStorageDataAccess()

        val petsRepo = PetRepository(petFake)
        val rescuerRepo = RescuerRepository(rescuerFake, rescuerStorage)

        viewModel = ViewModelProvider(this, RegisteredPetsViewModelFactory(petsRepo, rescuerRepo))[RegisteredPetsViewModel::class.java]
        petsAdapter = RegisteredPetsAdapter()
        binding.registeredPetsRecycler.adapter = petsAdapter

        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })

    }

    private fun updateUI(uiModel: RegisteredPetsViewModel.UiModel){
        binding.progress.visibility = if (uiModel is RegisteredPetsViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when(uiModel){
            is RegisteredPetsViewModel.UiModel.PetsContent -> setView(uiModel.pets)
        }
    }

    private fun setView(petsList: List<Pet>) {
        petsAdapter.items = petsList
    }



}