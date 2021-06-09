package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.testshared.mockAdopter
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllAdoptersUseCaseTest {
    @Mock
    lateinit var adopterRepository: AdopterRepository

    lateinit var getAllAdoptersUseCase: GetAllAdoptersUseCase

    @Before
    fun setUp(){
        getAllAdoptersUseCase = GetAllAdoptersUseCase(adopterRepository)
    }

    @Test
    fun `GetAllAdoptersUseCase invoke calls adopters from adopterRepository`(){
        runBlocking {

            val adopters = listOf(mockAdopter.copy())
            whenever(adopterRepository.getAllAdopters()).thenReturn(adopters)

            val result = getAllAdoptersUseCase.invoke()
            verify(adopterRepository).getAllAdopters()

            Assert.assertEquals(adopters,result)
        }
    }
}