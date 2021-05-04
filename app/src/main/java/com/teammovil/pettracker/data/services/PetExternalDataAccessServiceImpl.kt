package com.teammovil.pettracker.data.services

import com.google.firebase.firestore.FirebaseFirestore
import com.teammovil.pettracker.data.pet.PetExternalDataAccess
import com.teammovil.pettracker.domain.Evidence
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.tasks.await

class PetExternalDataAccessServiceImpl: PetExternalDataAccess {
    private val serviceFirebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getAllPatsFromRescuer(rescuerId: String): List<Pet> {
        try{
            val result = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).whereEqualTo(Constants.PET_RESCUER_ID_FIELD, rescuerId).get().await()
            return result.documents.map { Mapper.mapPet(it) }
        }
        catch (e: Exception){
            return listOf()
        }
    }

    override suspend fun getAllPetsFromAdopter(adopterId: String): List<Pet> {
        try{
            val result = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).whereEqualTo(Constants.PET_ADOPTER_ID_FIELD, adopterId).get().await()
            return result.documents.map { Mapper.mapPet(it) }
        }
        catch (e: Exception){
            return listOf()
        }
    }

    override suspend fun getPetById(petId: String): Pet {
        TODO("Not yet implemented")
    }

    override suspend fun registerPet(pet: Pet, rescuerId: String): Boolean {
        try {
            //Upload image and update url in pet

            //Generate Id
            val ref = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document()

            //Update new pet
            pet.id = ref.id
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(ref.id)
                .set(Mapper.map(pet).apply { put(Constants.PET_RESCUER_ID_FIELD, rescuerId)}).await()

            //Dewormings
            for (deworming in pet.dewormings) {
                val dewormingRef =
                    serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(ref.id)
                        .collection(Constants.DEWORMING_COLLECTION).document()
                deworming.id = dewormingRef.id
                serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(ref.id)
                    .collection(Constants.DEWORMING_COLLECTION).document(dewormingRef.id)
                    .set(Mapper.map(deworming)).await()
            }

            //Vaccines
            for (vaccine in pet.vaccines) {
                val vaccineRef =
                    serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(ref.id)
                        .collection(Constants.VACCINE_COLLECTION).document()
                vaccine.id = vaccineRef.id
                serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(ref.id)
                    .collection(Constants.VACCINE_COLLECTION).document(vaccineRef.id)
                    .set(Mapper.map(vaccine)).await()
            }
            return true

        }catch(e: Exception){
            return false
        }
    }

    override suspend fun updatePet(pet: Pet): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun assignAdopterToPet(petId: String, adopterId: String): Boolean {
        try {
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                .update(mapOf(Constants.PET_ADOPTER_ID_FIELD to adopterId)).await()
            return true
        }catch (e: Exception){
            return false
        }
    }

    override suspend fun saveEvidence(petId: String, evidence: Evidence): Boolean {
        TODO("Not yet implemented")
    }
}