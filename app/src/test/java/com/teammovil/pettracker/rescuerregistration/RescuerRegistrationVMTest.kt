package com.teammovil.pettracker.rescuerregistration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.domain.Result
import com.teammovil.pettracker.fakes.mockRescuerView
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.rescuerregistration.RescuerRegistrationViewModel
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RescuerRegistrationVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var registerRescuerUseCase : RegisterRescuerUseCase

    @Mock
    lateinit var observer: Observer<RescuerRegistrationViewModel.UiModel>

    private lateinit var vm: RescuerRegistrationViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        vm = RescuerRegistrationViewModel(registerRescuerUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun `when submit register button, loading is shown`() {
        runBlocking {
            whenever(registerRescuerUseCase.invoke(Mapper.map(mockRescuerView))).thenReturn(Result(Unit, null))
            vm.model.observeForever(observer)

            vm.onSaveRescuer(mockRescuerView)

            verify(observer).onChanged(RescuerRegistrationViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `when called use case, show success advice is called`() {
        runBlocking {
            whenever(registerRescuerUseCase.invoke(Mapper.map(mockRescuerView))).thenReturn(Result(Unit, null))
            vm.model.observeForever(observer)

            vm.onSaveRescuer(mockRescuerView)

            verify(observer).onChanged(RescuerRegistrationViewModel.UiModel.SuccessNotification(
                MessageValidation.RESCUER_REGISTER_SUCCESS))
        }
    }
}