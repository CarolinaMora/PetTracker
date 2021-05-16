package com.teammovil.usecases.loginrescuer

import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Rescuer
import com.teammovil.domain.Result

class LoginRescuerUseCase(private val rescuerRepository: RescuerRepository) {
    suspend fun invoke(user: String, password: String) = rescuerRepository.login(user, password)
}