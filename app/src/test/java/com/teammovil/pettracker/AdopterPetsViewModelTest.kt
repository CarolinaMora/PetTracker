package com.teammovil.pettracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.pettracker.ui.adopterpets.AdopterPetsViewModel
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AdopterPetsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: AdopterPetsViewModel

    @Mock
    lateinit var getAdopterPetsUseCase: GetAdopterPetsUseCase

    @Mock
    lateinit var observer: Observer<AdopterPetsViewModel.UiModel>

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        viewModel = AdopterPetsViewModel(getAdopterPetsUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun `when called use case, show list of adopter pets`(){
        runBlocking {
            val adopterPets = listOf(mockPet.copy())
            whenever(getAdopterPetsUseCase.invoke()).thenReturn(adopterPets)
            viewModel.model.observeForever(observer)
            viewModel.onStartView()
            verify(getAdopterPetsUseCase).invoke()
            verify(observer).onChanged(AdopterPetsViewModel.UiModel.Loading)
        }
    }


}