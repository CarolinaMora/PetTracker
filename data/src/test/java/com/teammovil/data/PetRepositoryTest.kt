package com.teammovil.data

import com.teammovil.data.pet.PetExternalDataAccess
import com.teammovil.data.pet.PetRepository
import org.junit.Before
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
    
}