package com.teammovil.pettracker.ui.petdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.teammovil.pettracker.data.pet.*
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentPetDetailBinding
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PET_ID= "petId"

/**
 * A simple [Fragment] subclass.
 * Use the [PetDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetDetailFragment: Fragment() {

    private var petId: String? = null

    private lateinit var binding: FragmentPetDetailBinding
    private lateinit var viewModel: PetDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getString(ARG_PET_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetDetailBinding.inflate (inflater)

        viewModel = ViewModelProvider(this,
                PetDetailViewModelFactory (PetRepository(PetExternalDataAccessServiceImpl()))
        ) [PetDetailViewModel::class.java]
        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUi(it)
        })

        petId?.let{
            viewModel.onGetPetDetail(it)
        }

        return binding.root
    }

    private fun updateUi (uiModel: PetDetailViewModel.UiModel) {
        //Activa / desactiva progress bar
        binding.prgProgress.visibility = if (uiModel is PetDetailViewModel.UiModel.Loading)  View.VISIBLE else View.GONE

        when (uiModel) {
            is PetDetailViewModel.UiModel.PetDetailContent -> setView(uiModel.pet)
        }

    }

    private fun setView(pet: Pet) {
        with(binding){

            txtName.text = pet.name
            txtGender.text= pet.gender.name
            txtRace.text = pet.race
            txtDesc.text = pet.description
            txtBirth.text = pet.approximateDateOfBirth.toString()
            txtRescue.text = pet.rescueDate.toString()
            txtType.text = pet.petType.name
            txtSterilized.text = pet.sterilized.toString()
            rvwVaccine.adapter = VaccinesAdapter (pet.vaccines)
            rvwDeworming.adapter = DewormingsAdapter(pet.dewormings.map{it.applicationDate})
            txtStatus.text = pet.status.name
            rvwEvidences.adapter = EvidencesAdapter (pet.evidences)
            Glide
                .with(binding.root.context)
                .load(pet.mainPhoto)
                .into(binding.imgPetPhoto)

        }

    }

}