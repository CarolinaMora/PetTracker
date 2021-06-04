package com.teammovil.domain.rules.adoptervalidator

import com.teammovil.domain.Adopter
import com.teammovil.domain.Result
import com.teammovil.domain.Error

interface AdopterValidator {
    fun validateAdopter(adopter: Adopter):Result<Unit,List<Error>>
}