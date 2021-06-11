package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.testshared.mockPet
import com.teammovil.testshared.mockRescuer
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetRescuerPetsTest {

    @Mock
    lateinit var petRepository: PetRepository

    @Mock
    lateinit var rescuerRepository: RescuerRepository

    lateinit var getRescuerPets: GetRescuerPets

    @Before
    fun setUp(){
        getRescuerPets = GetRescuerPets(petRepository, rescuerRepository)
    }

    @Test
    fun `pet repository exist`(){
        Assert.assertTrue(getRescuerPets.petRepository is PetRepository)
    }
    @Test
    fun `rescuer repository exist`(){
        Assert.assertTrue(getRescuerPets.rescuerRepository is RescuerRepository)
    }

    @Test
    fun `validate getAllPets from rescuerPets`(){
        runBlocking {
            val rescuerPets = listOf(mockPet.copy())
            val rescuer = mockRescuer
            whenever(rescuerRepository.getRescuer()).thenReturn(rescuer)
            whenever(petRepository.getAllPatsFromRescuer(rescuerId = mockRescuer.email)).thenReturn(rescuerPets)
            val result = getRescuerPets.invoke()
            Assert.assertEquals(rescuerPets, result)

        }
    }

}