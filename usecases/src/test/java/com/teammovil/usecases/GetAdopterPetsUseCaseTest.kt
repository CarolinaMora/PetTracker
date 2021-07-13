package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.testshared.mockAdopter
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAdopterPetsUseCaseTest {

    @Mock
    lateinit var petRepository: PetRepository

    @Mock
    lateinit var adopterRepository: AdopterRepository

    lateinit var getAdopterPets: GetAdopterPetsUseCase

    @Before
    fun setUp(){
        getAdopterPets = GetAdopterPetsUseCase(petRepository, adopterRepository)
    }

    @Test
    fun `retrieve pets from adopterPetsUseCase`(){
        runBlocking {
            val adopter = mockAdopter
            val adopterPets = listOf(mockPet.copy())
            whenever(adopterRepository.getAdopter()).thenReturn(adopter)
            whenever(petRepository.getAllPetsFromAdopter(adopterId = mockAdopter.email)).thenReturn(adopterPets)
            val result = getAdopterPets.invoke()
            Assert.assertEquals(adopterPets, result)
        }
    }
}