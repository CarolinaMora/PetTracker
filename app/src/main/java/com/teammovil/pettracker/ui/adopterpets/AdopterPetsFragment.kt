package com.teammovil.pettracker.ui.adopterpets

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.teammovil.pettracker.databinding.FragmentAdopterPetsBinding
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdopterPetsFragment : Fragment() {

    lateinit var binding: FragmentAdopterPetsBinding
    lateinit var petsAdapter: RegisteredPetsAdapter
    private val viewModel: AdopterPetsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAdopterPetsBinding.bind(view)

        petsAdapter = RegisteredPetsAdapter{
            onClickPet(it)
        }
        binding.adopterPetsRecycler.adapter = petsAdapter

        setObservers()
        viewModel.onStartView()
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

    private fun setView(petsList: List<com.teammovil.domain.Pet>){
        petsAdapter.items = petsList
    }


    private fun onClickPet (pet: com.teammovil.domain.Pet){
        viewModel.onDetailPet(pet)
    }

    private fun goToPetDetail (petId: String){
        val action = AdopterPetsFragmentDirections.actionAdopterPetsFragmentToPetDetailFragment(petId)
        view?.findNavController()?.navigate(action)
    }

}