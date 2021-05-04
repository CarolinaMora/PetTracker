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

    override suspend fun getPetById(petId: String): Pet? {
        try {
            //Get pet
            val petDocument = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId).get().await()
            val pet = Mapper.mapPet(petDocument)

            //Dewormings
            val dewormings = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                    .collection(Constants.DEWORMING_COLLECTION).get().await().documents
            pet.dewormings = dewormings.map { Mapper.mapDeworming(it) }

            //Vaccines
            val vaccines = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                .collection(Constants.VACCINE_COLLECTION).get().await().documents
            pet.vaccines = vaccines.map { Mapper.mapVaccine(it) }

            //Evidences
            val evidences = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                .collection(Constants.EVIDENCE_COLLECTION).get().await().documents
            pet.evidences = evidences.map { Mapper.mapEvidence(it) }

            return pet

        }catch(e: Exception){
            return null
        }
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
        try {
            //Update image and update url in pet

            //Update pet
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id)
                .update(Mapper.map(pet)).await()

            //Dewormings
            for (deworming in pet.dewormings) {
                if(deworming.id.isEmpty()) {
                    val dewormingRef =
                        serviceFirebaseFirestore.collection(Constants.PET_COLLECTION)
                            .document(pet.id)
                            .collection(Constants.DEWORMING_COLLECTION).document()
                    deworming.id = dewormingRef.id
                    serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id)
                        .collection(Constants.DEWORMING_COLLECTION).document(dewormingRef.id)
                        .set(Mapper.map(deworming)).await()
                }
                else{
                    serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id)
                        .collection(Constants.DEWORMING_COLLECTION).document(deworming.id)
                        .update(Mapper.map(deworming)).await()
                }
            }

            //Vaccines
            for (vaccine in pet.vaccines) {
                if(vaccine.id.isEmpty()) {
                    val vaccineRef =
                        serviceFirebaseFirestore.collection(Constants.PET_COLLECTION)
                            .document(pet.id)
                            .collection(Constants.VACCINE_COLLECTION).document()
                    vaccine.id = vaccineRef.id
                    serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id)
                        .collection(Constants.VACCINE_COLLECTION).document(vaccineRef.id)
                        .set(Mapper.map(vaccine)).await()
                }
                else{
                    serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id)
                        .collection(Constants.VACCINE_COLLECTION).document(vaccine.id)
                        .update(Mapper.map(vaccine)).await()
                }
            }
            return true

        }catch(e: Exception){
            return false
        }
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
        //Upload image and update url in evidence

        try {
            val evidenceRef =
                serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                    .collection(Constants.EVIDENCE_COLLECTION).document()
            evidence.id = evidenceRef.id
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                .collection(Constants.EVIDENCE_COLLECTION).document(evidenceRef.id)
                .set(Mapper.map(evidence)).await()
            return true
        }catch (e: Exception){
            return false
        }
    }
}