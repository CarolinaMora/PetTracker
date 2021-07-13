package com.teammovil.usecases.registeradopter

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.domain.Adopter
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.domain.rules.adoptervalidator.AdopterValidator
import com.teammovil.domain.rules.adoptervalidator.AdopterValidatorImp
import com.teammovil.usecases.common.UseCaseErrors

class RegisterAdopterUseCase(var adopterRepository: AdopterRepository): AdopterValidator by AdopterValidatorImp() {

    suspend fun invoke(input: Adopter): Result<Unit, List<Error>>{
        val resultAdopterValidation = validateAdopter(input)
        if(resultAdopterValidation.valid){
            val registerAdopterResultSuccess = adopterRepository.registerAdopter(input)
            if (registerAdopterResultSuccess)
                return Result(Unit,null)
            else
                return Result(null, listOf(Error(UseCaseErrors.REGISTER_ADOPTER_GENERIC_ERROR)))
        }else{
            return Result(null,resultAdopterValidation.error)
        }
    }
}