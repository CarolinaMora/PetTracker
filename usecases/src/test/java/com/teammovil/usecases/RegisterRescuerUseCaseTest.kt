package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.testshared.mockRescuer
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
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
    fun `RegisterRescuerUseCase property rescuerRepository exists` (){
        Assert.assertTrue(registerRescuerUseCase.rescuerRepository is RescuerRepository)
    }

    @Test
    fun `RegisterRescuerUseCase valid when validation, registerRescuer return ok` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerRescuerUseCase)
            whenever(spiedUseCase.validateRescuer(mockRescuer)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.registerRescuer(mockRescuer)).thenReturn(true)

            val result = spiedUseCase.invoke(mockRescuer).success != null

            verify(spiedUseCase).validateRescuer(mockRescuer)
            verify(rescuerRepository).registerRescuer(mockRescuer)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `RegisterRescuerUseCase invalid when validate pet returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerRescuerUseCase)
            whenever(spiedUseCase.validateRescuer(mockRescuer)).thenReturn(Result(null, listOf(Error(0))))

            val result = spiedUseCase.invoke(mockRescuer).success != null

            verify(spiedUseCase).validateRescuer(mockRescuer)
            Assert.assertFalse(result)
        }
    }

    @Test
    fun `RegisterRescuerUseCase invalid when registerRescuer returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerRescuerUseCase)
            whenever(spiedUseCase.validateRescuer(mockRescuer)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.registerRescuer(mockRescuer)).thenReturn(false)

            val result = spiedUseCase.invoke(mockRescuer).success != null

            verify(spiedUseCase).validateRescuer(mockRescuer)
            verify(rescuerRepository).registerRescuer(mockRescuer)
            Assert.assertFalse(result)
        }
    }
}