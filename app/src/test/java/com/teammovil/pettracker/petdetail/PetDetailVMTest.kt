package com.teammovil.pettracker.petdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.domain.Result
import com.teammovil.pettracker.fakes.mockPetView
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.petdetail.PetDetailViewModel
import com.teammovil.usecases.petdetail.GetPetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class PetDetailVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Mock
    lateinit var petDetailUseCase: GetPetUseCase

    @Mock
    lateinit var observer: Observer<PetDetailViewModel.UiModel>

    private lateinit var vm: PetDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vm = PetDetailViewModel(petDetailUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun `when display pet, loading is shown`() {
        runBlocking {
            whenever(petDetailUseCase.invoke(mockPetView.id)).thenReturn(Mapper.mapPet(mockPetView))

            vm.onGetPetDetail(mockPetView.id)

            verify(petDetailUseCase).invoke(mockPetView.id)


        }
    }
}