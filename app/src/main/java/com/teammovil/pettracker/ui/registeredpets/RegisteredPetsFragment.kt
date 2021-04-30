package com.teammovil.pettracker.ui.registeredpets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentRegisteredPetsBinding
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.petdetail.ARG_PET_ID


/**
 * A simple [Fragment] subclass.
 */
class RegisterPetsFragment : Fragment() {

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
    }

    private fun updateUI(uiModel: RegisteredPetsViewModel.UiModel){
        binding.progress.visibility = if (uiModel is RegisteredPetsViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when(uiModel){
            is RegisteredPetsViewModel.UiModel.PetsContent -> updateList(uiModel.pets)
        }
    }

    private fun updateList (petsList: List<Pet>) {
        petsAdapter.items = petsList
    }

    private fun onClickPet (pet: Pet){
        val bundle = bundleOf(Pair(ARG_PET_ID, pet.id ))
        view?.findNavController()
            ?.navigate(R.id.action_registerPetsFragment_to_petDetailFragment, bundle)
    }

    private fun onClickAddPet (){
        view?.findNavController()
            ?.navigate(R.id.action_registerPetsFragment_to_petRegistrationFragment)
    }

}