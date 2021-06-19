package com.teammovil.pettracker.editpet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.fakes.fakePetList
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.pettracker.ui.editregisterpet.EditRegisterPetViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EditPetIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<PetView>

    lateinit var vm : EditRegisterPetViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeEditPetViewModel
    }

    @Test
    fun `pet data is loaded from server when is editing`() {
        runBlocking {
            vm.petView.observeForever(observer)

            vm.onStartView(fakePetList[0].id)

            verify(observer).onChanged(any())
        }
    }

    @Test
    fun `pet data is not loaded from server when is register`() {
        runBlocking {
            vm.petView.observeForever(observer)

            vm.onStartView(null)

            verify(observer, never()).onChanged(any())
        }
    }
}