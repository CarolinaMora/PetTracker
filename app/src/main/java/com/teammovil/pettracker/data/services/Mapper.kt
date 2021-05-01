package com.teammovil.pettracker.data.services

import com.google.firebase.firestore.DocumentSnapshot
import com.teammovil.pettracker.domain.Adopter
import com.teammovil.pettracker.domain.GenderType
import com.teammovil.pettracker.domain.Rescuer
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
}