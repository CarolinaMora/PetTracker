package com.teammovil.usecases.registeradopter

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.domain.Adopter
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.domain.rules.AdopterValidator
import com.teammovil.usecases.common.UseCaseErrors

class RegisterAdopterUseCase(var adopterRepository: AdopterRepository) {

    suspend fun invoke(input: Adopter): Result<Unit, List<Error>>{
        val resultAdopterValidation = AdopterValidator.validateAdopter(input)
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