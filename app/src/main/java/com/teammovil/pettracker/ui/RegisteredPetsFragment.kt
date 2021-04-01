package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeStorageDataAccess
import com.teammovil.pettracker.databinding.FragmentRegisteredPetsBinding
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterPetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterPetsFragment : Fragment() {

    lateinit var binding: FragmentRegisteredPetsBinding
    lateinit var petsAdapter: RegisteredPetsAdapter
    var petFake = PetFakeExternalDataAccess()
    var rescuerFake = RescuerFakeExternalDataAccess()
    var rescuerStorage = RescuerFakeStorageDataAccess()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisteredPetsBinding.inflate(inflater)
        petsAdapter = RegisteredPetsAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registeredPetsRecycler.adapter = petsAdapter
        getDummyList()
    }

    private fun getDummyList() {
        /*var data = object : PetExternalDataAccess{
            override suspend fun getAllPatsFromRescuer(rescuerId: String): List<Pet> {
                return getPets()
            }

            override suspend fun getPetById(petId: String): Pet {
                TODO("Not yet implemented")
            }

            override suspend fun registerPet(pet: Pet): Boolean {
                TODO("Not yet implemented")
            }

        }*/

        val repo = PetRepository(petFake)
        val rescuerRepo = RescuerRepository(rescuerFake, rescuerStorage)
        viewLifecycleOwner.lifecycleScope.launch {
            var rescuer = rescuerRepo.getRescuer()
            var result = repo.getAllPatsFromRescuer(rescuer.id)
            setView(result)
        }

    }

    private fun setView(petsList: List<Pet>) {
        petsAdapter.items = petsList
    }


}