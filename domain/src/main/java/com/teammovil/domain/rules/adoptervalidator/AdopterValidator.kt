package com.teammovil.domain.rules.adoptervalidator

import com.teammovil.domain.*

interface AdopterValidator {
    fun validateAdopter(adopter: Adopter):Result<Unit,List<Error>>
}