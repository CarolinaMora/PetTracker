package com.teammovil.usecases.loginrescuer

import com.teammovil.data.rescuer.RescuerRepository

class LoginRescuerUseCase(private val rescuerRepository: RescuerRepository) {
    suspend fun invoke(user: String, password: String) = rescuerRepository.login(user, password)
}