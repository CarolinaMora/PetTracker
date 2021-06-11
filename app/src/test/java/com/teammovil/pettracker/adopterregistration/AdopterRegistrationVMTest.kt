package com.teammovil.pettracker.adopterregistration

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.domain.Result
import com.teammovil.pettracker.fakes.mockAdopterView
import com.teammovil.pettracker.ui.adopterregistration.AdopterRegistrationViewModel
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AdopterRegistrationVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var registerAdopterUseCase: RegisterAdopterUseCase

    @Mock
    lateinit var observer: Observer<AdopterRegistrationViewModel.UiModel>

    private lateinit var vm:AdopterRegistrationViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        vm =  AdopterRegistrationViewModel(registerAdopterUseCase,Dispatchers.Unconfined)
    }

    @Test
    fun `When click register button, loading is called`(){
        runBlocking {
            whenever(registerAdopterUseCase.invoke(Mapper.map(mockAdopterView))).thenReturn(Result(Unit,null))
            vm.model.observeForever(observer)

            vm.onSaveAdopter(mockAdopterView)

            verify(observer).onChanged(AdopterRegistrationViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `When called use case, show success notification is called`(){
        runBlocking {
            whenever(registerAdopterUseCase.invoke(Mapper.map(mockAdopterView))).thenReturn(Result(Unit,null))
            vm.model.observeForever(observer)

            vm.onSaveAdopter(mockAdopterView)

            verify(observer).onChanged(AdopterRegistrationViewModel.UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS))
        }
    }
}