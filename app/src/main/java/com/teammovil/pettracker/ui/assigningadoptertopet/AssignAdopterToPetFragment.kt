package com.teammovil.pettracker.ui.assigningadoptertopet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.adopter.AdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterStorageDataAccess
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.databinding.FragmentAssignAdopterToPetBinding
import com.teammovil.pettracker.domain.Adopter

class AssignAdopterToPetFragment : Fragment(R.layout.fragment_assign_adopter_to_pet) {

    private lateinit var binding: FragmentAssignAdopterToPetBinding
    private lateinit var viewModel: AdopterViewModel
    private lateinit var adoptersAdapter: AdopterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAssignAdopterToPetBinding.bind(view)

        val petFake = PetFakeExternalDataAccess()
        val adopterFake = FakeAdopterExternalDataAccess()
        val adopterStorage = FakeAdopterStorageDataAccess()

        val petRepository = PetRepository(petFake)
        val adopterRepository = AdopterRepository(adopterFake,adopterStorage)

        viewModel = ViewModelProvider(this,AdopterViewModelFactory(petRepository,adopterRepository))[AdopterViewModel::class.java]
        adoptersAdapter = AdopterAdapter()
        binding.adopterRecycler.adapter = adoptersAdapter

        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }

    private fun updateUI(uiModel: AdopterViewModel.UiModel){
        binding.progress.visibility = if(uiModel is AdopterViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when(uiModel){
            is AdopterViewModel.UiModel.AdoptersContent -> setView(uiModel.adopter)
        }
    }

    private fun setView(adopterList: List<Adopter>){
        adoptersAdapter.items = adopterList
    }

    //Crear un método que tenga cómo objetivo mandar el petId y el adopterID una ves que el usuario haya confirmado la asiganación en el viewModel se manda a llamar
    //Para hacer le Match es necesario mandar el petId y el adopterID, con eso deberia.

}