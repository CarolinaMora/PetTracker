package com.teammovil.pettracker.domain

import java.util.*

data class Pet (
    var id: String,
    var name: String,
    var gender: GenderType,
    var race: String,
    var description: String,
    var approximateDateOfBirth: Date,
    var rescueDate: Date,
    var petType: PetType,
    var sterilized: Boolean,
    var vaccines: List<Vaccine>,
    var dewormings: List<Deworming>,
    var mainPhoto: String,
    var status: PetStatus,
    var evidences: List<Evidence>
    )


data class Vaccine(
    var id: String,
    var name: String,
    var applicationDate: Date
)

data class Evidence(
    var id: String,
    var comment: String?,
    var media: String,
    var date: Date
)

data class Deworming(
    var id: String,
    var name: String,
    var applicationDate: Date
)