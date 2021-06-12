package com.teammovil.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetExternalDataAccess
import com.teammovil.data.pet.PetRepository
import com.teammovil.testshared.mockEvidence
import com.teammovil.testshared.mockPet
import com.teammovil.testshared.mockRescuer
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