package com.teammovil.pettracker.rescuerregistration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.fakes.mockRescuerView
import com.teammovil.pettracker.ui.rescuerregistration.RescuerRegistrationViewModel
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
class RescuerRegistrationIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<RescuerRegistrationViewModel.UiModel>

    lateinit var vm : RescuerRegistrationViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeRescuerRegistrationViewModel
    }

    @Test
    fun `rescuer data is register in server`() {
        runBlocking {
            vm.model.observeForever(observer)

            vm.onSaveRescuer(mockRescuerView)

            verify(observer).onChanged(RescuerRegistrationViewModel.UiModel.SuccessNotification(MessageValidation.RESCUER_REGISTER_SUCCESS))
        }
    }
}