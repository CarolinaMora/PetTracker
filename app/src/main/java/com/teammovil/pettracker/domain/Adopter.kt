package com.teammovil.pettracker.domain

import java.util.*

data class Adopter (
    var id: String,
    var name: String,
    var firstLastName: String,
    var secondLastName: String,
    var gender: GenderType,
    var BirthDate: Date,
    var email: String,
    var password: String,
    var phone: String,
    var address: String
    )