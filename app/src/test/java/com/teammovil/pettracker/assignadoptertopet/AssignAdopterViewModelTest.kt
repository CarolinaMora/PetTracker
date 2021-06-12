package com.teammovil.pettracker.assignadoptertopet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.pettracker.ui.assigningadoptertopet.AdopterViewModel
import com.teammovil.pettracker.util.MessageValidation
import com.teammovil.testshared.mockAdopter
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AssignAdopterViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var assignAdopterToPetUseCase: AssignAdopterToPetUseCase

    @Mock
    lateinit var getAllAdoptersUseCase: GetAllAdoptersUseCase

    @Mock
    lateinit var observer: Observer<AdopterViewModel.UiModel>

    private lateinit var vm: AdopterViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        vm = AdopterViewModel(assignAdopterToPetUseCase,getAllAdoptersUseCase,Dispatchers.Unconfined)
    }

    @Test
    fun `when called get all adopters use case, getAllAdopters is called`(){
        runBlocking {
            val adopters = listOf(mockAdopter.copy())

            whenever(getAllAdoptersUseCase.invoke()).thenReturn(adopters)
            vm.model.observeForever(observer)

            vm.onStartView()
            verify(getAllAdoptersUseCase).invoke()
            verify(observer).onChanged(AdopterViewModel.UiModel.AdoptersContent(adopters))
        }
    }

    @Test
    fun `when called assign adopter use case, show success notification is called`(){
        runBlocking {
            whenever(assignAdopterToPetUseCase.invoke(petId= mockPet.id,adopterId = mockAdopter.email)).thenReturn(true)
            vm.model.observeForever(observer)

            vm.onAssignAdopterToPet(adopterId = mockAdopter.email,petId = mockPet.id)
            verify(assignAdopterToPetUseCase).invoke(petId= mockPet.id,adopterId = mockAdopter.email)
            verify(observer).onChanged(AdopterViewModel.UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS))
        }
    }

}