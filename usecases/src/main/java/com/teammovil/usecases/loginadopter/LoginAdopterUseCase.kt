package com.teammovil.usecases.loginadopter

import com.teammovil.data.adopter.AdopterRepository

class LoginAdopterUseCase(private val adopterRepository: AdopterRepository) {
    suspend fun invoke(user: String, password: String) = adopterRepository.login(user, password)
}