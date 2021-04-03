package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.pet.*
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.databinding.FragmentPetDetailBinding
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.domain.PetType
import com.teammovil.pettracker.domain.Vaccine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PetDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetDetailBinding.inflate (inflater)
        getPet()

        return binding.root
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
            rvwDeworming.adapter = DewormingsAdapter(pet.dewormings)
            txtStatus.text = pet.status.name
            rvwEvidences.adapter = EvidencesAdapter (pet.evidences)
            Glide
                    .with(binding.root.context)
                    .load(pet.mainPhoto)
                    .into(binding.imgPetPhoto)

        }

    }

    private fun getPet() {
        val petRepository = PetRepository(PetFakeExternalDataAccess())
        viewLifecycleOwner.lifecycleScope.launch{

            var resultPet = petRepository.getPetById("1")
            setView(resultPet)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PetDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PetDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}