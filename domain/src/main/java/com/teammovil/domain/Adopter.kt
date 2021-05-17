package com.teammovil.domain

import java.util.*

data class Adopter (
    var email: String,
    var name: String,
    var firstLastName: String,
    var secondLastName: String,
    var gender: GenderType,
    var BirthDate: Date?,
    var password: String,
    var phone: String,
    var address: String
    )