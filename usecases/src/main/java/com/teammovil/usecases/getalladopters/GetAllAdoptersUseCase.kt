package com.teammovil.usecases.getalladopters

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.domain.Adopter

class GetAllAdoptersUseCase(val adopterRepository: AdopterRepository) {
    suspend fun invoke():List<Adopter> = adopterRepository.getAllAdopters()
}
