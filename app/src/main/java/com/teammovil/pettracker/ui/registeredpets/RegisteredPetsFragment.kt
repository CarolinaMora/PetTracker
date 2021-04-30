package com.teammovil.pettracker.ui.registeredpets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentRegisteredPetsBinding
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.common.EventObserver


/**
 * A simple [Fragment] subclass.
 */
class RegisteredPetsFragment : Fragment() {

    lateinit var binding: FragmentRegisteredPetsBinding
    lateinit var petsAdapter: RegisteredPetsAdapter
    private lateinit var viewModel: RegisteredPetsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisteredPetsBinding.inflate(inflater)

        val petFake = PetFakeExternalDataAccess()
        val rescuerFake = RescuerFakeExternalDataAccess()
        val rescuerStorage = RescuerFakeStorageDataAccess()

        val petsRepo = PetRepository(petFake)
        val rescuerRepo = RescuerRepository(rescuerFake, rescuerStorage)

        viewModel = ViewModelProvider(this, RegisteredPetsViewModelFactory(petsRepo, rescuerRepo))[RegisteredPetsViewModel::class.java]

        setViews()
        setListeners()
        setObservers()

        return binding.root
    }

    private fun setViews (){
        petsAdapter = RegisteredPetsAdapter{
            onClickPet(it)
        }
        binding.registeredPetsRecycler.adapter = petsAdapter
    }

    private fun setListeners (){
        binding.resgisteredPetsAdd.setOnClickListener {
            onClickAddPet()
        }
    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
        viewModel.events.observe(viewLifecycleOwner, EventObserver{
            updateUI(it)
        })
    }

    private fun updateUI(uiModel: RegisteredPetsViewModel.UiModel){
        binding.progress.visibility = if (uiModel is RegisteredPetsViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when(uiModel){
            is RegisteredPetsViewModel.UiModel.PetsContent -> updateList(uiModel.pets)
        }
    }

    private fun updateUI (uiEvents: RegisteredPetsViewModel.UiEvents){
        when(uiEvents){
            is RegisteredPetsViewModel.UiEvents.GoToDetail -> goToPetDetail(uiEvents.petId)
            is RegisteredPetsViewModel.UiEvents.GoTORegistration -> goToPetRegistration()
        }
    }

    private fun updateList (petsList: List<Pet>) {
        petsAdapter.items = petsList
    }

    private fun onClickPet (pet: Pet){
        viewModel.onDetailPet(pet)
    }

    private fun onClickAddPet (){
        viewModel.onRegisterPet()
    }

    private fun goToPetDetail (petId: String){
        val action = RegisteredPetsFragmentDirections.actionRegisterPetsFragmentToPetDetailFragment(petId)
        view?.findNavController()?.navigate(action)
    }

    private fun goToPetRegistration (){
        val action = RegisteredPetsFragmentDirections.actionRegisterPetsFragmentToPetRegistrationFragment()
        view?.findNavController()?.navigate(action)
    }

}