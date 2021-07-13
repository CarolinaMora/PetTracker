package com.teammovil.pettracker.data.services

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.teammovil.data.pet.PetExternalDataAccess
import kotlinx.coroutines.tasks.await
import java.io.File

class PetExternalDataAccessServiceImpl:
    PetExternalDataAccess {
    private val serviceFirebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val serviceFirebaseStorage = Firebase.storage.reference

    override suspend fun getAllPatsFromRescuer(rescuerId: String): List<com.teammovil.domain.Pet> {
        try{
            val result = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).whereEqualTo(Constants.PET_RESCUER_ID_FIELD, rescuerId).get().await()
            return result.documents.map { Mapper.mapPet(it) }
        }
        catch (e: Exception){
            return listOf()
        }
    }

    override suspend fun getAllPetsFromAdopter(adopterId: String): List<com.teammovil.domain.Pet> {
        try{
            val result = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).whereEqualTo(Constants.PET_ADOPTER_ID_FIELD, adopterId).get().await()
            return result.documents.map { Mapper.mapPet(it) }
        }
        catch (e: Exception){
            return listOf()
        }
    }

    override suspend fun getPetById(petId: String): com.teammovil.domain.Pet? {
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

    override suspend fun registerPet(pet: com.teammovil.domain.Pet, rescuerId: String): Boolean {
        try {
            //Generate Id
            val ref = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document()
            pet.id = ref.id

            //Upload image and update url in pet
            val fileName = getFileName(pet.mainPhoto)?:""
            val urlFile = uploadRemoteFile(pet.mainPhoto, "${Constants.PETS_ROOT_URL}${pet.id}/")
            urlFile?.let {pet.mainPhoto = it}

            //Update new pet
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(ref.id)
                .set(Mapper.map(pet, fileName).apply { put(Constants.PET_RESCUER_ID_FIELD, rescuerId)}).await()

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

    override suspend fun updatePet(pet: com.teammovil.domain.Pet): Boolean {
        try {

            //Update image and update url in pet
            //Get file name from remote
            val petDocument = serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id).get().await()
            var fileName = if(petDocument.contains(Constants.PET_PHOTO_FILE_NAME_ID_FIELD))
                petDocument.get(Constants.PET_PHOTO_FILE_NAME_ID_FIELD).toString() else ""
            if(isLocalFile(pet.mainPhoto)) {
                //Delete remote file
                deleteRemoteFile(fileName, "${Constants.PETS_ROOT_URL}${pet.id}/")
                fileName = getFileName(pet.mainPhoto) ?: ""
                //Upload new file
                val urlFile = uploadRemoteFile(pet.mainPhoto, "${Constants.PETS_ROOT_URL}${pet.id}/")
                urlFile?.let { pet.mainPhoto = it }
            }

            //Update pet
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(pet.id)
                .update(Mapper.map(pet, fileName)).await()

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
                .update(mapOf(
                    Constants.PET_ADOPTER_ID_FIELD to adopterId,
                    Constants.PET_STATUS_FIELD to com.teammovil.domain.PetStatus.ADOPTED.ordinal)
                ).await()
            return true
        }catch (e: Exception){
            return false
        }
    }

    override suspend fun saveEvidence(petId: String, evidence: com.teammovil.domain.Evidence): Boolean {
        //Upload image and update url in evidence
        val fileName = getFileName(evidence.media)?:""
        val urlFile = uploadRemoteFile(evidence.media, "${Constants.PETS_ROOT_URL}${petId}/${Constants.EVIDENCES_ROOT_URL}")
        urlFile?.let {evidence.media = it}

        try {
            val evidenceRef =
                serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                    .collection(Constants.EVIDENCE_COLLECTION).document()
            evidence.id = evidenceRef.id
            serviceFirebaseFirestore.collection(Constants.PET_COLLECTION).document(petId)
                .collection(Constants.EVIDENCE_COLLECTION).document(evidenceRef.id)
                .set(Mapper.map(evidence, fileName)).await()
            return true
        }catch (e: Exception){
            return false
        }
    }

    private suspend fun uploadRemoteFile (urlFile: String, parentPathRef: String): String?{
        val file = File(urlFile)
        if (file.exists()) {
            try {
                val fileUri = Uri.fromFile(file)
                val ref = serviceFirebaseStorage.child("$parentPathRef${fileUri.lastPathSegment}")
                ref.putFile(fileUri).await()
                return ref.downloadUrl.await().toString()
            } catch (e: Exception) {
                return null
            }
        } else return null
    }

    private suspend fun deleteRemoteFile (fileName: String, parentPathRef: String){
        try {
            val ref = serviceFirebaseStorage.child("$parentPathRef$fileName")
            ref.delete().await()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun isLocalFile (urlFile: String): Boolean{
        return !urlFile.contains(Regex("gs|https"))
    }

    private fun getFileName (urlFile: String): String?{
        return Uri.fromFile(File(urlFile)).lastPathSegment
    }

}