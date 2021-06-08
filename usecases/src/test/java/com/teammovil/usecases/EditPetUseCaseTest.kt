package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.editpet.EditPetUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditPetUseCaseTest {

    @Mock
    lateinit var petRepository: PetRepository

    lateinit var editPetUseCase: EditPetUseCase

    @Before
    fun setUp (){
        editPetUseCase = EditPetUseCase(petRepository)
    }

    @Test
    fun `EditPetUseCase property petRepository exists` (){
        Assert.assertTrue(editPetUseCase.petRepository is PetRepository)
    }

    @Test
    fun `EditPetUseCase valid when validation, updatePet return ok` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(editPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(Unit, null))
            whenever(petRepository.updatePet(mockPet)).thenReturn(true)

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            verify(petRepository).updatePet(mockPet)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `EditPetUseCase invalid when validate pet returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(editPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(null, listOf(Error(0))))

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            Assert.assertFalse(result)
        }
    }

    @Test
    fun `EditPetUseCase invalid when updatePet returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(editPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(Unit, null))
            whenever(petRepository.updatePet(mockPet)).thenReturn(false)

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            verify(petRepository).updatePet(mockPet)
            Assert.assertFalse(result)
        }
    }

}