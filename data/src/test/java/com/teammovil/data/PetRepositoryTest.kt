package com.teammovil.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetExternalDataAccess
import com.teammovil.data.pet.PetRepository
import com.teammovil.testshared.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PetRepositoryTest {

    @Mock
    lateinit var petExternalDataAccess: PetExternalDataAccess

    lateinit var petRepository: PetRepository

    @Before
    fun setUp (){
        petRepository = PetRepository(petExternalDataAccess)
    }

    @Test
    fun `getAllPatsFromRescuer calls external data access`() {
        runBlocking {
            whenever(petExternalDataAccess.getAllPatsFromRescuer(mockRescuer.id)).thenReturn(mockPetList)

            val result = petRepository.getAllPatsFromRescuer(mockRescuer.id)

            verify(petExternalDataAccess).getAllPatsFromRescuer(mockRescuer.id)
            assertEquals(result, mockPetList)

        }
    }

    @Test
    fun `getAllPetsFromAdopter calls external data access`() {
        runBlocking {
            whenever(petExternalDataAccess.getAllPetsFromAdopter(mockAdopter.email)).thenReturn(mockPetList)

            val result = petRepository.getAllPetsFromAdopter(mockRescuer.email)

            verify(petExternalDataAccess).getAllPetsFromAdopter(mockRescuer.email)
            assertEquals(result, mockPetList)

        }
    }

    @Test
    fun `getPetById calls external data access`() {
        runBlocking {
            whenever(petExternalDataAccess.getPetById(mockPet.id)).thenReturn(mockPet)

            val result = petRepository.getPetById(mockPet.id)

            verify(petExternalDataAccess).getPetById(mockPet.id)
            assertEquals(result, mockPet)

        }
    }

    @Test
    fun `register pet calls external data access` (){
        runBlocking {
            whenever(petExternalDataAccess.registerPet(mockPet, mockRescuer.email)).thenReturn(true)

            val result = petRepository.registerPet(mockPet, mockRescuer.email)

            verify(petExternalDataAccess).registerPet(mockPet, mockRescuer.email)
            assertTrue(result)
        }
    }

    @Test
    fun `update pet calls external data access` (){
        runBlocking {
            whenever(petExternalDataAccess.updatePet(mockPet)).thenReturn(true)

            val result = petRepository.updatePet(mockPet)

            verify(petExternalDataAccess).updatePet(mockPet)
            assertTrue(result)
        }
    }

    @Test
    fun `assign adopter to pet calls external data access` (){
        runBlocking {
            whenever(petExternalDataAccess.assignAdopterToPet(mockPet.id, mockRescuer.email)).thenReturn(true)

            val result = petRepository.assignAdopterToPet(mockPet.id, mockRescuer.email)

            verify(petExternalDataAccess).assignAdopterToPet(mockPet.id, mockRescuer.email)
            assertTrue(result)
        }
    }

    @Test
    fun `save evidence calls external data access` (){
        runBlocking {
            whenever(petExternalDataAccess.saveEvidence(mockPet.id, mockEvidence)).thenReturn(true)

            val result = petRepository.saveEvidence(mockPet.id, mockEvidence)

            verify(petExternalDataAccess).saveEvidence(mockPet.id, mockEvidence)
            assertTrue(result)
        }
    }
    
}