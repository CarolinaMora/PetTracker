package com.teammovil.usecases.registerpet

import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Pet
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.domain.rules.PetValidator
import com.teammovil.usecases.common.UseCaseErrors

class RegisterPetUseCase(
    var rescuerRepository: RescuerRepository,
    var petRepository: PetRepository
) {

    suspend fun invoke(input: Pet) : Result<Unit, List<Error>> {
        val resultPet = PetValidator.validatePet(input)
        if(resultPet.valid) {
            val resultRescuer = rescuerRepository.getRescuer()
            if(resultRescuer!=null) {
                val result = petRepository.registerPet(input, resultRescuer.email)
                return if (result)
                    Result(Unit, null)
                else
                    Result(
                        null,
                        listOf(Error(UseCaseErrors.REGISTER_PET_GENERIC_ERROR))
                    )
            }
            return Result(null, listOf(Error(UseCaseErrors.REGISTER_PET_GENERIC_ERROR)))
        }
        else
            return Result(null, resultPet.error)
    }
}