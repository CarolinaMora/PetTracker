package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.testshared.mockEvidence
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
class SendEvidenceUseCaseTest {
    @Mock
    lateinit var petRepository: PetRepository

    lateinit var sendEvidenceUseCase: SaveEvidenceUseCase

    @Before
    fun setUp (){
        sendEvidenceUseCase = SaveEvidenceUseCase(petRepository)
    }

    @Test
    fun `SendEvidenceUseCase property petRepository exists` (){
        Assert.assertTrue(sendEvidenceUseCase.petRepository is PetRepository)
    }

    @Test
    fun `SendEvidenceUseCase valid when validation, saveEvidence return ok` (){
        runBlocking {
            val spiedUseCase = spy(sendEvidenceUseCase)
            whenever(spiedUseCase.validate(mockEvidence)).thenReturn(Result(Unit, null))
            whenever(petRepository.saveEvidence("1", mockEvidence)).thenReturn(true)

            val result = spiedUseCase.invoke("1", mockEvidence).valid

            verify(spiedUseCase).validate(mockEvidence)
            verify(petRepository).saveEvidence("1", mockEvidence)

            Assert.assertTrue(result)
        }
    }

    @Test
    fun `SendEvidenceUseCase invalid when sendEvidence returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(sendEvidenceUseCase)
            whenever(spiedUseCase.validate(mockEvidence)).thenReturn(Result(null, listOf(Error(0))))

            val result = spiedUseCase.invoke("1", mockEvidence).valid

            verify(spiedUseCase).validate(mockEvidence)
            Assert.assertFalse(result)
        }
    }

}