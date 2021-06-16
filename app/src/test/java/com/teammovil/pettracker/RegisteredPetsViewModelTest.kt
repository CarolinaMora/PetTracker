package com.teammovil.pettracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModel
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RegisteredPetsViewModelTest {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewmodel: RegisteredPetsViewModel

    @Mock
    lateinit var getRescuerPets: GetRescuerPets

    @Mock
    lateinit var observer: Observer<RegisteredPetsViewModel.UiModel>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewmodel = RegisteredPetsViewModel(getRescuerPets, Dispatchers.Unconfined)
    }

    @Test
    fun `when called use case, show list of rescuer pets`(){
        runBlocking {
            val rescuerPets = listOf(mockPet.copy())
            whenever(getRescuerPets.invoke()).thenReturn(rescuerPets)
            viewmodel.model.observeForever(observer)
            viewmodel.onStartView()
            verify(getRescuerPets).invoke()
            verify(observer).onChanged(RegisteredPetsViewModel.UiModel.Loading)
        }
    }
}