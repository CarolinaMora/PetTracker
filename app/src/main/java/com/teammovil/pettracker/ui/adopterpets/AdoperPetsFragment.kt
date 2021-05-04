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
import com.teammovil.pettracker.data.database.dataaccess.AdopterStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.services.AdopterExternalDataAccessServiceImpl
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentAdopterPetsBinding
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsAdapter

class AdopterPetsFragment : Fragment() {

    lateinit var binding: FragmentAdopterPetsBinding
    lateinit var petsAdapter: RegisteredPetsAdapter
    private lateinit var viewModel: AdopterPetsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdopterPetsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val petExternal = PetExternalDataAccessServiceImpl()
        val adopterExternal = AdopterExternalDataAccessServiceImpl()
        val adopterStorage = AdopterStorageDataAccessDataBaseImpl(requireContext())

        val petsRepo = PetRepository(petExternal)
        val adopterRepo = AdopterRepository(adopterExternal, adopterStorage)

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