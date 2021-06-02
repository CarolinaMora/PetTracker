package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Result
import com.teammovil.domain.rules.PetValidator
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.editpet.EditPetUseCase
import kotlinx.coroutines.runBlocking
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
    fun `EditPetUseCase updatePet repository called` (){
        runBlocking {
            whenever(petRepository.updatePet(mockPet)).thenReturn(true)

            editPetUseCase.invoke(mockPet)

            verify(petRepository).updatePet(mockPet)
        }
    }

}