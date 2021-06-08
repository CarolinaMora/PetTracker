package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.testshared.mockPet
import com.teammovil.testshared.mockRescuer
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisterPetUseCaseTest {

    @Mock
    lateinit var rescuerRepository: RescuerRepository

    @Mock
    lateinit var petRepository: PetRepository

    lateinit var registerPetUseCase: RegisterPetUseCase

    @Before
    fun setUp (){
        MockitoAnnotations.initMocks(this)
        registerPetUseCase = RegisterPetUseCase(rescuerRepository, petRepository)
    }

    @Test
    fun `RegisterPetUseCase property rescuerRepository exists` (){
        assertTrue(registerPetUseCase.rescuerRepository is RescuerRepository)
    }

    @Test
    fun `RegisterPetUseCase property petRepository exists` (){
        assertTrue(registerPetUseCase.petRepository is PetRepository)
    }

    @Test
    fun `RegisterPetUseCase valid when validation, registerPet and getRescuer return ok` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.getRescuer()).thenReturn(mockRescuer)
            whenever(petRepository.registerPet(any(), any())).thenReturn(true)

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            verify(rescuerRepository).getRescuer()
            verify(petRepository).registerPet(any(), any())
            assertTrue(result)
        }
    }

    @Test
    fun `RegisterPetUseCase invalid when validate pet returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(null, listOf(Error(0))))

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            assertFalse(result)
        }
    }

    @Test
    fun `RegisterPetUseCase invalid when getRescuer returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.getRescuer()).thenReturn(null)

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            verify(rescuerRepository).getRescuer()
            assertFalse(result)
        }
    }

    @Test
    fun `RegisterPetUseCase invalid when registerPet returns failure` (){
        runBlocking {
            val spiedUseCase = com.nhaarman.mockitokotlin2.spy(registerPetUseCase)
            whenever(spiedUseCase.validatePet(mockPet)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.getRescuer()).thenReturn(mockRescuer)
            whenever(petRepository.registerPet(any(), any())).thenReturn(false)

            val result = spiedUseCase.invoke(mockPet).success != null

            verify(spiedUseCase).validatePet(mockPet)
            verify(rescuerRepository).getRescuer()
            verify(petRepository).registerPet(any(), any())
            assertFalse(result)
        }
    }

}