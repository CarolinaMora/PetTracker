package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.testshared.mockRescuer
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterRescuerUseCaseTest {

    @Mock
    lateinit var rescuerRepository: RescuerRepository

    lateinit var registerRescuerUseCase: RegisterRescuerUseCase

    @Before
    fun setUp (){
        registerRescuerUseCase = RegisterRescuerUseCase(rescuerRepository)
    }

    @Test
    fun `RegisterRescuerUseCase registerRescuer repository called` (){
        runBlocking {
            whenever(rescuerRepository.registerRescuer(mockRescuer)).thenReturn(true)

            registerRescuerUseCase.invoke(mockRescuer)

            verify(rescuerRepository).registerRescuer(mockRescuer)
        }
    }
}