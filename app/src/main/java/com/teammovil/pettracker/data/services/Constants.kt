package com.teammovil.pettracker.data.services

object Constants {

    const val RESCUER_COLLECTION = "rescuers"
    const val ADOPTER_COLLECTION = "adopters"
    const val PET_COLLECTION = "pets"
    const val VACCINE_COLLECTION = "vaccines"
    const val DEWORMING_COLLECTION = "dewormings"
    const val EVIDENCE_COLLECTION = "evidences"

    //Rescuer
    const val RESCUER_NAME_FIELD = "name"
    const val RESCUER_DESCRIPTION_FIELD = "description"
    const val RESCUER_ADDRESS_FIELD = "address"
    const val RESCUER_EMAIL_FIELD = "email"
    const val RESCUER_PASSWORD_FIELD = "password"
    const val RESCUER_PHONE_FIELD = "phone"
    const val RESCUER_START_DATE_FIELD = "activityStartDate"

    //Rescuer
    const val ADOPTER_NAME_FIELD = "name"
    const val ADOPTER_FIRST_LAST_NAME_FIELD = "first_last_name"
    const val ADOPTER_SECOND_LAST_NAME_FIELD = "second_last_name"
    const val ADOPTER_GENDER_FIELD = "gender"
    const val ADOPTER_BIRTH_DATE = "birthdate"
    const val ADOPTER_EMAIL_FIELD = "email"
    const val ADOPTER_PASSWORD_FIELD = "password"
    const val ADOPTER_PHONE_FIELD = "phone"
    const val ADOPTER_ADDRESS_FIELD = "address"

    //Pet
    const val PET_ID_FIELD = "id"
    const val PET_NAME_FIELD = "name"
    const val PET_GENDER_FIELD = "gender"
    const val PET_RACE_FIELD = "race"
    const val PET_DESCRIPTION_FIELD = "description"
    const val PET_BIRTH_DATE_FIELD = "birth_date"
    const val PET_RESCUE_DATE_FIELD = "rescue_date"
    const val PET_TYPE_FIELD = "type"
    const val PET_STERILIZED_FIELD = "sterilized"
    const val PET_PHOTO_URL_FIELD = "url"
    const val PET_STATUS_FIELD = "status"
    const val PET_RESCUER_ID_FIELD = "rescuerId"
    const val PET_ADOPTER_ID_FIELD = "adopterId"

    //Vaccine
    const val VACCINE_ID_FIELD = "id"
    const val VACCINE_NAME_FIELD = "name"
    const val VACCINE_APPLICATION_DATE_FIELD = "application_date"

    //Evicence
    const val EVIDENCE_ID_FIELD = "id"
    const val EVIDENCE_COMMENT_FIELD = "comment"
    const val EVIDENCE_MADIA_URL_FIELD = "url"
    const val EVIDENCE_DATE_TAKEN_FIELD = "date_taken"

    //Deworming
    const val DEWORMING_ID_FIELD = "id"
    const val DEWORMING_NAME_FIELD = "name"
    const val DEWORMING_APPLICATION_FIELD = "application_date"

}