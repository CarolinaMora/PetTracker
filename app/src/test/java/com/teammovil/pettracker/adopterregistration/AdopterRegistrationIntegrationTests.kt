package com.teammovil.pettracker.adopterregistration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.domain.Adopter
import com.teammovil.pettracker.fakes.FakeAdopterExternalDataAccess
import org.junit.Assert.assertTrue
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.fakes.fakeAdopterList
import com.teammovil.pettracker.fakes.mockAdopterView
import com.teammovil.pettracker.ui.adopterregistration.AdopterRegistrationViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AdopterRegistrationIntegrationTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<AdopterRegistrationViewModel.UiModel>

    lateinit var vm: AdopterRegistrationViewModel

    lateinit var adopterDataAccess: FakeAdopterExternalDataAccess


    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeAdopterRegistrationViewModel
    }

    @Test
    fun `loading is called when onSaveAdopter is pressed`(){
        runBlocking {
            vm.model.observeForever(observer)

            vm.onSaveAdopter(mockAdopterView)

            verify(observer).onChanged(AdopterRegistrationViewModel.UiModel.Loading)
            //assertTrue(adopterDataAccess.registerAdopter(fakeAdopterList[1]))
        }
    }

}