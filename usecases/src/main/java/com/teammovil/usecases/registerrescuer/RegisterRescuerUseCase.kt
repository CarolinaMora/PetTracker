package com.teammovil.usecases.registerrescuer

import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Rescuer
import com.teammovil.domain.Result
import com.teammovil.domain.Error
import com.teammovil.domain.rules.RescuerValidator
import com.teammovil.domain.rules.RescuerValidatorImpl
import com.teammovil.usecases.common.UseCaseErrors

class RegisterRescuerUseCase(
    val rescuerRepository: RescuerRepository
): RescuerValidator by RescuerValidatorImpl() {

    suspend fun invoke (input: Rescuer) : Result<Unit, List<Error>>{
        val rescuerValidationResult = validateRescuer(input)
        if(rescuerValidationResult.valid){
            val registerRescuerResultSuccess = rescuerRepository.registerRescuer(input)
            if(registerRescuerResultSuccess)
                return Result(Unit, null)
            else
                return Result(null, listOf(Error(UseCaseErrors.REGISTER_RESCUER_GENERIC_ERROR)))
        }
        else
            return Result(null, rescuerValidationResult.error)
    }
}