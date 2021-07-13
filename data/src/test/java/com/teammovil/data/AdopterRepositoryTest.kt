package com.teammovil.data

import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.adopter.AdopterExternalDataAccess
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.adopter.AdopterStorageDataAccess
import com.teammovil.testshared.mockAdopter
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdopterRepositoryTest {

    @Mock
    lateinit var adopterExternalDataAccess: AdopterExternalDataAccess

    @Mock
    lateinit var adopterStorageDataAccess: AdopterStorageDataAccess

    lateinit var adopterRepository: AdopterRepository

    @Before
    fun setUp() {
        adopterRepository = AdopterRepository(adopterExternalDataAccess, adopterStorageDataAccess)
    }

    @Test
    fun `getAllAdopters gets from external data access`() {
        runBlocking {
            val externalAdopters = listOf(mockAdopter)
            whenever(adopterExternalDataAccess.getAllAdopters()).thenReturn(externalAdopters)

            val result = adopterRepository.getAllAdopters()

            assertEquals(externalAdopters, result)
        }
    }

    @Test
    fun `registerAdopter saves external data access`() {
        runBlocking {
            whenever(adopterExternalDataAccess.registerAdopter(mockAdopter)).thenReturn(true)

            val result = adopterRepository.registerAdopter(mockAdopter)

            assertTrue(result)
        }
    }

    @Test
    fun `getAdopter gets from storage data access`() {
        runBlocking {
            val adopter = mockAdopter
            whenever(adopterStorageDataAccess.getAdopter()).thenReturn(adopter)

            val result = adopterRepository.getAdopter()

            assertEquals(adopter, result)
        }
    }

    @Test
    fun `login called when the adopter login in successfully`() {
        runBlocking {
            val adopter = mockAdopter.copy()
            whenever(adopterExternalDataAccess.login(mockAdopter.email, mockAdopter.password)).thenReturn(adopter)

            val result = adopterRepository.login(mockAdopter.email, mockAdopter.password)

            assertTrue(result)
        }
    }

}