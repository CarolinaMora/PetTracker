package com.teammovil.usecases.editpet

import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Pet
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.domain.rules.PetValidator
import com.teammovil.domain.rules.PetValidatorImpl
import com.teammovil.usecases.common.UseCaseErrors

class EditPetUseCase(
    var petRepository: PetRepository
): PetValidator by PetValidatorImpl() {

    suspend fun invoke(input: Pet) : Result<Unit, List<Error>> {
        val resultPet = validatePet(input)
        if(resultPet.valid) {
            val result = petRepository.updatePet(input)
            return if (result)
                Result(Unit, null)
            else
                Result(null, listOf(Error(UseCaseErrors.EDIT_PET_GENERIC_ERROR)))
        }
        else
            return Result(null, resultPet.error)
    }
}