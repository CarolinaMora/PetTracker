package com.teammovil.pettracker.petdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.fakes.mockPetView
import com.teammovil.pettracker.ui.petdetail.PetDetailViewModel
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.Observer
import com.teammovil.pettracker.fakes.fakePetList
import com.teammovil.pettracker.ui.common.Mapper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PetDetailIntegrationTests {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<PetDetailViewModel.UiModel>

    lateinit var vm: PetDetailViewModel


    @Before
    fun init(){
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakePetDetailViewModel
    }

    @Test
    fun `PetDetailContent is called when onGetPetDetail is called` () {
        runBlocking {
            vm.model.observeForever(observer)

            vm.onGetPetDetail(fakePetList[0].id)


            verify(observer).onChanged(PetDetailViewModel.UiModel.PetDetailContent(fakePetList[0]))

        }

    }
}