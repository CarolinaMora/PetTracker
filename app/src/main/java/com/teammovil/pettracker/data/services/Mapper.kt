package com.teammovil.pettracker.data.services

import com.google.firebase.firestore.DocumentSnapshot
import com.teammovil.pettracker.domain.*
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.getStringFromDate
import java.util.*
import kotlin.collections.HashMap

object Mapper {

    fun map (rescuer: Rescuer): HashMap<String, Any?>{
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

    fun mapRescuer (document: DocumentSnapshot): Rescuer{
        return Rescuer(
            "",
            document.get(Constants.RESCUER_NAME_FIELD) as String? ?:"",
            document.get(Constants.RESCUER_DESCRIPTION_FIELD) as String? ?:"",
            document.get(Constants.RESCUER_ADDRESS_FIELD) as String? ?:"",
            document.get(Constants.RESCUER_EMAIL_FIELD) as String? ?:"",
            document.get(Constants.RESCUER_PASSWORD_FIELD) as String? ?:"",
            document.get(Constants.RESCUER_PHONE_FIELD) as String? ?:"",
            getDateFromString(document.get(Constants.RESCUER_START_DATE_FIELD) as String?) ?:Date()
        )
    }

    fun map(adopter: Adopter): HashMap<String, Any?>{
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

    fun mapAdopter (document: DocumentSnapshot): Adopter{
        return Adopter(
            "",
            document.get(Constants.ADOPTER_NAME_FIELD) as String? ?:"",
            document.get(Constants.ADOPTER_FIRST_LAST_NAME_FIELD) as String? ?:"",
            document.get(Constants.ADOPTER_SECOND_LAST_NAME_FIELD) as String? ?:"",
            GenderType.values()[(document.get(Constants.ADOPTER_GENDER_FIELD) as Long?)?.toInt() ?: 0],
            getDateFromString(document.get(Constants.ADOPTER_BIRTH_DATE) as String?) ?:Date(),
            document.get(Constants.ADOPTER_EMAIL_FIELD) as String? ?:"",
            document.get(Constants.ADOPTER_PASSWORD_FIELD) as String? ?:"",
            document.get(Constants.ADOPTER_PHONE_FIELD) as String? ?:"",
            document.get(Constants.ADOPTER_ADDRESS_FIELD) as String? ?:""
        )
    }

    fun map (pet: Pet):  HashMap<String, Any?>{
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
            Constants.EVIDENCE_MADIA_URL_FIELD to pet.mainPhoto
        )
    }

    fun mapPet (document: DocumentSnapshot): Pet{
        return Pet(
            document.get(Constants.PET_ID_FIELD) as String? ?:"",
            document.get(Constants.PET_NAME_FIELD) as String? ?:"",
            GenderType.values()[(document.get(Constants.PET_GENDER_FIELD) as Long? ?: 0).toInt()],
            document.get(Constants.PET_RACE_FIELD) as String? ?:"",
            document.get(Constants.PET_DESCRIPTION_FIELD) as String? ?:"",
            getDateFromString(document.get(Constants.PET_BIRTH_DATE_FIELD) as String? ?:"") ?: Date(),
                getDateFromString(document.get(Constants.PET_RESCUE_DATE_FIELD) as String? ?:"") ?: Date(),
            PetType.values()[(document.get(Constants.PET_TYPE_FIELD) as Long? ?: 0).toInt()],
            document.get(Constants.PET_STERILIZED_FIELD) as Boolean? ?: false,
            listOf(),
            listOf(),
            document.get(Constants.PET_PHOTO_URL_FIELD) as String? ?:"",
            PetStatus.values()[(document.get(Constants.PET_STATUS_FIELD) as Long? ?: 0).toInt()],
            listOf()
        )
    }

    fun map (vaccine: Vaccine): HashMap<String, Any?> {
        return hashMapOf(
            Constants.VACCINE_ID_FIELD to vaccine.id,
            Constants.VACCINE_NAME_FIELD to vaccine.name,
            Constants.VACCINE_APPLICATION_DATE_FIELD to getStringFromDate(vaccine.applicationDate)
        )
    }

    fun map (deworming: Deworming): HashMap<String, Any?> {
        return hashMapOf(
            Constants.DEWORMING_ID_FIELD to deworming.id,
            Constants.DEWORMING_NAME_FIELD to deworming.name,
            Constants.DEWORMING_APPLICATION_FIELD to getStringFromDate(deworming.applicationDate)
        )
    }
}