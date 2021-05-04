package com.teammovil.pettracker.ui.adopterpets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterStorageDataAccess
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.databinding.FragmentAdoperPetsBinding
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsAdapter

class AdoperPetsFragment : Fragment() {

    lateinit var binding: FragmentAdoperPetsBinding
    lateinit var petsAdapter: RegisteredPetsAdapter
    private lateinit var viewModel: AdopterPetsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdoperPetsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val petFake = PetFakeExternalDataAccess()
        val adopterFake = FakeAdopterExternalDataAccess()
        val adopterStorage = FakeAdopterStorageDataAccess()

        val petsRepo = PetRepository(petFake)
        val adopterRepo = AdopterRepository(adopterFake, adopterStorage)

        viewModel = ViewModelProvider(this, AdopterPetsViewModelFactory(petsRepo, adopterRepo))[AdopterPetsViewModel::class.java]
        petsAdapter = RegisteredPetsAdapter{
            onClickPet(it)
        }
        binding.adopterPetsRecycler.adapter = petsAdapter

        setObservers()
    }

    private fun setObservers(){
        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            goToPetDetail(it)
        })
    }

    private fun updateUI(uiModel: AdopterPetsViewModel.UiModel){
        binding.adopterPetsProgress.visibility = if (uiModel is AdopterPetsViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when(uiModel){
            is AdopterPetsViewModel.UiModel.AdopterPetsContent -> setView(uiModel.pets)
        }
    }

    private fun setView(petsList: List<Pet>){
        petsAdapter.items = petsList
    }


    private fun onClickPet (pet: Pet){
        viewModel.onDetailPet(pet)
    }

    private fun goToPetDetail (petId: String){
        val action = AdoperPetsFragmentDirections.actionAdoperPetsFragmentToPetDetailFragment(petId)
        view?.findNavController()?.navigate(action)
    }

}