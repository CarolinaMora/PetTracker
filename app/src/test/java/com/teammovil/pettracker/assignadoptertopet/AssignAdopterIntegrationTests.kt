package com.teammovil.pettracker.assignadoptertopet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.fakes.*
import com.teammovil.pettracker.ui.assigningadoptertopet.AdopterViewModel
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
class AssignAdopterIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<AdopterViewModel.UiModel>

    lateinit var vm: AdopterViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeAssignAdopterToPetViewModel
    }

    @Test
    fun `loading is called when onStartView method is call`(){
        runBlocking {
            vm.model.observeForever(observer)

            vm.onStartView()

            verify(observer).onChanged(AdopterViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `assig adopter data is register in server`(){
        runBlocking {
            vm.model.observeForever(observer)

            vm.onAssignAdopterToPet(fakeAdopterList[0].email, fakePetList[0].id)

            verify(observer).onChanged(AdopterViewModel.UiModel.Loading)
            verify(observer).onChanged(AdopterViewModel.UiModel.SuccessNotification(MessageValidation.ADOPTER_REGISTER_SUCCESS))
        }
    }

}