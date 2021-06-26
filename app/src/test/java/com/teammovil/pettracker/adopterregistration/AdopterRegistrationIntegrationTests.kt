package com.teammovil.pettracker.adopterregistration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.fakes.mockAdopterView
import com.teammovil.pettracker.ui.adopterregistration.AdopterRegistrationViewModel
import com.teammovil.pettracker.util.MessageValidation
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


    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeAdopterRegistrationViewModel
    }

    @Test
    fun `adopter data is register in server`(){
        runBlocking {
            vm.model.observeForever(observer)

            vm.onSaveAdopter(mockAdopterView)

            verify(observer).onChanged(AdopterRegistrationViewModel.UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS))
        }
    }

}