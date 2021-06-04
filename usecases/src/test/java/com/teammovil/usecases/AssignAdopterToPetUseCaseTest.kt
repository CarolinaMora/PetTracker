package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.testshared.mockAdopter
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AssignAdopterToPetUseCaseTest {

    @Mock
    lateinit var petRepository: PetRepository

    lateinit var assignAdopterToPetUseCase: AssignAdopterToPetUseCase

    @Before
    fun setUp(){
        assignAdopterToPetUseCase = AssignAdopterToPetUseCase(petRepository)
    }

    @Test
    fun `invoke calls assignAdopter`(){
        runBlocking {

            whenever(petRepository.assignAdopterToPet(petId= mockPet.id,adopterId = mockAdopter.email)).thenReturn(true)

            val result = assignAdopterToPetUseCase.invoke(petId = mockPet.id,adopterId = mockAdopter.email)
            verify(petRepository).assignAdopterToPet(petId = mockPet.id,adopterId = mockAdopter.email)

            Assert.assertTrue(result)
        }
    }
}