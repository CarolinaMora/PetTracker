package com.teammovil.pettracker.data.services

import com.google.firebase.firestore.DocumentSnapshot
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.getStringFromDate
import java.util.*
import kotlin.collections.HashMap

object Mapper {

    fun map (rescuer: com.teammovil.domain.Rescuer): HashMap<String, Any?>{
        return hashMapOf(
            Constants.RESCUER_NAME_FIELD to rescuer.name,
            Constants.RESCUER_DESCRIPTION_FIELD to rescuer.description,
            Constants.RESCUER_ADDRESS_FIELD to rescuer.address,
            Constants.RESCUER_EMAIL_FIELD to rescuer.email,
            Constants.RESCUER_PASSWORD_FIELD to rescuer.password,
            Constants.RESCUER_PHONE_FIELD to rescuer.phone,
            Constants.RESCUER_START_DATE_FIELD to getStringFromDate(rescuer.activityStartDate)
        )
    }

    fun mapRescuer (document: DocumentSnapshot): com.teammovil.domain.Rescuer {
        return com.teammovil.domain.Rescuer(
            "",
            document.get(Constants.RESCUER_NAME_FIELD) as String? ?: "",
            document.get(Constants.RESCUER_DESCRIPTION_FIELD) as String? ?: "",
            document.get(Constants.RESCUER_ADDRESS_FIELD) as String? ?: "",
            document.get(Constants.RESCUER_EMAIL_FIELD) as String? ?: "",
            document.get(Constants.RESCUER_PASSWORD_FIELD) as String? ?: "",
            document.get(Constants.RESCUER_PHONE_FIELD) as String? ?: "",
            getDateFromString(document.get(Constants.RESCUER_START_DATE_FIELD) as String?) ?: Date()
        )
    }

    fun map(adopter: com.teammovil.domain.Adopter): HashMap<String, Any?>{
        return hashMapOf(
            Constants.ADOPTER_NAME_FIELD to adopter.name,
            Constants.ADOPTER_FIRST_LAST_NAME_FIELD to adopter.firstLastName,
            Constants.ADOPTER_SECOND_LAST_NAME_FIELD to adopter.secondLastName,
            Constants.ADOPTER_GENDER_FIELD to adopter.gender.ordinal,
            Constants.ADOPTER_BIRTH_DATE to getStringFromDate(adopter.BirthDate),
            Constants.ADOPTER_EMAIL_FIELD to adopter.email,
            Constants.ADOPTER_PASSWORD_FIELD to adopter.password,
            Constants.ADOPTER_PHONE_FIELD to adopter.phone,
            Constants.ADOPTER_ADDRESS_FIELD to adopter.address
        )
    }

    fun mapAdopter (document: DocumentSnapshot): com.teammovil.domain.Adopter {
        return com.teammovil.domain.Adopter(
            document.get(Constants.ADOPTER_EMAIL_FIELD) as String? ?: "",
            document.get(Constants.ADOPTER_NAME_FIELD) as String? ?: "",
            document.get(Constants.ADOPTER_FIRST_LAST_NAME_FIELD) as String? ?: "",
            document.get(Constants.ADOPTER_SECOND_LAST_NAME_FIELD) as String? ?: "",
            com.teammovil.domain.GenderType.values()[(document.get(Constants.ADOPTER_GENDER_FIELD) as Long?)?.toInt()
                ?: 0],
            getDateFromString(document.get(Constants.ADOPTER_BIRTH_DATE) as String?) ?: Date(),
            document.get(Constants.ADOPTER_PASSWORD_FIELD) as String? ?: "",
            document.get(Constants.ADOPTER_PHONE_FIELD) as String? ?: "",
            document.get(Constants.ADOPTER_ADDRESS_FIELD) as String? ?: ""
        )
    }

    fun map (pet: com.teammovil.domain.Pet, fileName: String):  HashMap<String, Any?>{
        return hashMapOf(
            Constants.PET_ID_FIELD to pet.id,
            Constants.PET_NAME_FIELD to pet.name,
            Constants.PET_DESCRIPTION_FIELD to pet.description,
            Constants.PET_GENDER_FIELD to pet.gender.ordinal,
            Constants.PET_RACE_FIELD to pet.race,
            Constants.PET_BIRTH_DATE_FIELD to getStringFromDate(pet.approximateDateOfBirth),
            Constants.PET_RESCUE_DATE_FIELD to getStringFromDate(pet.rescueDate),
            Constants.PET_TYPE_FIELD to pet.petType.ordinal,
            Constants.PET_STERILIZED_FIELD to pet.sterilized,
            Constants.PET_STATUS_FIELD to pet.status.ordinal,
            Constants.PET_PHOTO_URL_FIELD to pet.mainPhoto,
            Constants.PET_PHOTO_FILE_NAME_ID_FIELD to fileName

        )
    }

    fun mapPet (document: DocumentSnapshot): com.teammovil.domain.Pet {
        return com.teammovil.domain.Pet(
            document.get(Constants.PET_ID_FIELD) as String? ?: "",
            document.get(Constants.PET_NAME_FIELD) as String? ?: "",
            com.teammovil.domain.GenderType.values()[(document.get(Constants.PET_GENDER_FIELD) as Long?
                ?: 0).toInt()],
            document.get(Constants.PET_RACE_FIELD) as String? ?: "",
            document.get(Constants.PET_DESCRIPTION_FIELD) as String? ?: "",
            getDateFromString(document.get(Constants.PET_BIRTH_DATE_FIELD) as String? ?: "")
                ?: Date(),
            getDateFromString(document.get(Constants.PET_RESCUE_DATE_FIELD) as String? ?: "")
                ?: Date(),
            com.teammovil.domain.PetType.values()[(document.get(Constants.PET_TYPE_FIELD) as Long?
                ?: 0).toInt()],
            document.get(Constants.PET_STERILIZED_FIELD) as Boolean? ?: false,
            listOf(),
            listOf(),
            document.get(Constants.PET_PHOTO_URL_FIELD) as String? ?: "",
            com.teammovil.domain.PetStatus.values()[(document.get(Constants.PET_STATUS_FIELD) as Long?
                ?: 0).toInt()],
            listOf()
        )
    }

    fun map (vaccine: com.teammovil.domain.Vaccine): HashMap<String, Any?> {
        return hashMapOf(
            Constants.VACCINE_ID_FIELD to vaccine.id,
            Constants.VACCINE_NAME_FIELD to vaccine.name,
            Constants.VACCINE_APPLICATION_DATE_FIELD to getStringFromDate(vaccine.applicationDate)
        )
    }

    fun map (deworming: com.teammovil.domain.Deworming): HashMap<String, Any?> {
        return hashMapOf(
            Constants.DEWORMING_ID_FIELD to deworming.id,
            Constants.DEWORMING_NAME_FIELD to deworming.name,
            Constants.DEWORMING_APPLICATION_FIELD to getStringFromDate(deworming.applicationDate)
        )
    }

    fun mapDeworming(document: DocumentSnapshot): com.teammovil.domain.Deworming {
        return com.teammovil.domain.Deworming(
            document.get(Constants.DEWORMING_ID_FIELD) as String? ?: "",
            document.get(Constants.DEWORMING_NAME_FIELD) as String? ?: "",
            getDateFromString(document.get(Constants.DEWORMING_APPLICATION_FIELD) as String? ?: "")
                ?: Date()
        )
    }

    fun mapVaccine(document: DocumentSnapshot): com.teammovil.domain.Vaccine {
        return com.teammovil.domain.Vaccine(
            document.get(Constants.VACCINE_ID_FIELD) as String? ?: "",
            document.get(Constants.VACCINE_NAME_FIELD) as String? ?: "",
            getDateFromString(
                document.get(Constants.VACCINE_APPLICATION_DATE_FIELD) as String? ?: ""
            ) ?: Date()
        )
    }

    fun mapEvidence(document: DocumentSnapshot): com.teammovil.domain.Evidence {
        return com.teammovil.domain.Evidence(
            document.get(Constants.EVIDENCE_ID_FIELD) as String? ?: "",
            document.get(Constants.EVIDENCE_COMMENT_FIELD) as String? ?: "",
            document.get(Constants.EVIDENCE_MADIA_URL_FIELD) as String? ?: "",
            getDateFromString(document.get(Constants.EVIDENCE_DATE_TAKEN_FIELD) as String? ?: "")
                ?: Date()
        )
    }

    fun map (evidence: com.teammovil.domain.Evidence, fileName: String): HashMap<String, Any?> {
        return hashMapOf(
            Constants.EVIDENCE_ID_FIELD to evidence.id,
            Constants.EVIDENCE_COMMENT_FIELD to evidence.comment,
            Constants.EVIDENCE_MADIA_URL_FIELD to evidence.media,
            Constants.EVIDENCE_DATE_TAKEN_FIELD to getStringFromDate(evidence.date),
            Constants.EVIDENCE_MEDIA_FILE_NAME_FIELD to fileName
        )
    }
}