package com.teammovil.usecases

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.domain.Result
import com.teammovil.testshared.mockAdopter
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterAdopterUseCaseTest {

    @Mock
    lateinit var adopterRepository: AdopterRepository

    lateinit var registerAdopterUseCase: RegisterAdopterUseCase

    @Before
    fun setUp(){
        registerAdopterUseCase = RegisterAdopterUseCase(adopterRepository)
    }

    @Test
    fun `RegisterAdopterUseCase adopterRepository exists`(){
        Assert.assertTrue(registerAdopterUseCase.adopterRepository is AdopterRepository)
    }

    @Test
    fun `RegisterAdopterUseCase when registerAdopter is success`(){
        runBlocking {
            val adopterUseCase = spy(registerAdopterUseCase)
            whenever(adopterUseCase.validateAdopter(mockAdopter)).thenReturn(Result(Unit,null))
            whenever(adopterRepository.registerAdopter(mockAdopter)).thenReturn(true)

            val result = adopterUseCase.invoke(mockAdopter).success != null

            verify(adopterUseCase).validateAdopter(mockAdopter)
            verify(adopterRepository).registerAdopter(mockAdopter)
            Assert.assertTrue(result)
        }

    }
}