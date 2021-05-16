package com.teammovil.usecases

import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Evidence
import com.teammovil.domain.Result
import com.teammovil.domain.Error
import com.teammovil.domain.rules.EvidenceValidator
import com.teammovil.usecases.common.UseCaseErrors

class SaveEvidenceUseCase (
    private val petRepository: PetRepository) {

    suspend fun invoke (petId:String?, evidence: Evidence) : Result<Unit, List<Error>> {
        val validationResult = EvidenceValidator.validate(evidence)

        if (validationResult.valid) {

            if (petId == null)
                return Result(null,
                              listOf(Error(UseCaseErrors.SEND_EVIDENCE_GENERIC_ERROR)))

            val result = petRepository.saveEvidence(petId, evidence)

            return if (result)
                Result(Unit, null)
                else
                Result(
                    null,
                    listOf(Error(UseCaseErrors.SEND_EVIDENCE_GENERIC_ERROR))
                )
        }
        else
            return Result (null, validationResult.error )
    }
    
}