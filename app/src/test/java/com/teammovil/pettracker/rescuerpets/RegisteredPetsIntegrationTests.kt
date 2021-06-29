package com.teammovil.pettracker.rescuerpets

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.fakes.fakePetList
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegisteredPetsIntegrationTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<RegisteredPetsViewModel.UiModel>

    lateinit var vm: RegisteredPetsViewModel

    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeregisteredpetsViewModel
    }

    @Test
    fun `loading is called when onStartView is called`(){
        runBlocking {
            vm.model.observeForever(observer)

            vm.onStartView()

            verify(observer).onChanged(RegisteredPetsViewModel.UiModel.PetsContent(fakePetList))
        }
    }
}