package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.petdetail.GetPetUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PetDetailUseCaseTest {
    @Mock
    lateinit var petRepository: PetRepository

    lateinit var getPetUseCase: GetPetUseCase

    @Before
    fun setUp (){
        MockitoAnnotations.initMocks(this)
        getPetUseCase = GetPetUseCase(petRepository)
    }

    @Test
    fun `PetDetailUseCase property petRepository exists` (){
        Assert.assertTrue( getPetUseCase.petRepository is PetRepository)
    }

    @Test
    fun `PetDetailUseCase getPetById` (){
        runBlocking {

            val pet = mockPet.copy(id = "3")
            whenever(petRepository.getPetById("3")).thenReturn(pet)

            val result = getPetUseCase.invoke("3")

            assertEquals(pet, result)
        }
    }
}