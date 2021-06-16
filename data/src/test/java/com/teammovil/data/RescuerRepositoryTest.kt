package com.teammovil.data

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.rescuer.RescuerExternalDataAccess
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.data.rescuer.RescuerStorageDataAccess
import com.teammovil.testshared.mockRescuer
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RescuerRepositoryTest {

    @Mock
    lateinit var externalDataAccess: RescuerExternalDataAccess

    @Mock
    lateinit var storageDataAccess: RescuerStorageDataAccess

    lateinit var rescuerRepository: RescuerRepository

    @Before
    fun setUp(){
        rescuerRepository = RescuerRepository(externalDataAccess, storageDataAccess)
    }

    @Test
    fun `is valid when rescuer logged in RescuerRepository`(){
        runBlocking {
            val rescuer = mockRescuer
            whenever(externalDataAccess.login(rescuer.email, rescuer.password)).thenReturn(rescuer)
            val result = rescuerRepository.login(rescuer.email, rescuer.password)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `get a rescuer in RescuerRepository`(){
        runBlocking {
            val rescuer = mockRescuer
            whenever(storageDataAccess.getRescuer()).thenReturn(rescuer)
            val result = rescuerRepository.getRescuer()
            Assert.assertEquals(rescuer, result)
        }
    }

    @Test
    fun `register rescuer is valid in RescuerRepository`(){
        runBlocking {
            val rescuer = mockRescuer
            whenever(externalDataAccess.registerRescuer(rescuer)).thenReturn(true)
            val result = rescuerRepository.registerRescuer(rescuer)
            Assert.assertTrue(result)
        }
    }
}
