package com.teammovil.pettracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.pettracker.fakes.mockPetView
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.pettracker.ui.editregisterpet.EditRegisterPetViewModel
import com.teammovil.testshared.mockPet
import com.teammovil.usecases.common.UseCaseErrors
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class EditPetVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var editPetUseCase : EditPetUseCase

    @Mock
    lateinit var registerPetUseCase : RegisterPetUseCase

    @Mock
    lateinit var getPetUseCase: GetPetUseCase

    @Mock
    lateinit var observer: Observer<EditRegisterPetViewModel.UiModel>

    private lateinit var vm: EditRegisterPetViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        vm = EditRegisterPetViewModel(editPetUseCase, getPetUseCase, registerPetUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun `when start view with petId, get pet is called and loading is shown`() {
        runBlocking {
            val petId = mockPet.id
            whenever(getPetUseCase.invoke(petId)).thenReturn(mockPet)
            vm.model.observeForever(observer)

            vm.onStartView(petId)

            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.Loading(true))
            verify(getPetUseCase).invoke(petId)
        }
    }

    @Test
    fun `when start view without petId, get pet is not called and loading is not shown`() {
        runBlocking {
            vm.model.observeForever(observer)

            vm.onStartView(null)

            verify(observer, never()).onChanged(EditRegisterPetViewModel.UiModel.Loading(true))
            verify(getPetUseCase, never()).invoke(any())
        }
    }

    @Test
    fun `when save button clicked and is editing, edit pet is called and loading is shown`() {
        runBlocking {
            val petView = mockPetView.copy()
            val pet = Mapper.mapPet(petView)
            whenever(editPetUseCase.invoke(pet)).thenReturn(Result(Unit, null))
            vm.model.observeForever(observer)

            vm.onSavePet(petView)

            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.Loading(true))
            verify(editPetUseCase).invoke(pet)
        }
    }

    @Test
    fun `when save button clicked and is editing, call edit use case correct`() {
        runBlocking {
            val petView = mockPetView.copy()
            val pet = Mapper.mapPet(petView)
            whenever(editPetUseCase.invoke(pet)).thenReturn(Result(Unit, null))
            vm.model.observeForever(observer)

            vm.onSavePet(petView)

            verify(editPetUseCase).invoke(pet)
            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.SuccessAdvice)
        }
    }

    @Test
    fun `when save button clicked and is editing, call edit use case incorrect`() {
        runBlocking {
            val petView = mockPetView.copy()
            val pet = Mapper.mapPet(petView)
            whenever(editPetUseCase.invoke(pet)).thenReturn(Result(null, listOf(Error(UseCaseErrors.EDIT_PET_GENERIC_ERROR))))
            vm.model.observeForever(observer)

            vm.onSavePet(petView)

            verify(editPetUseCase).invoke(pet)
            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.ErrorAdvice(R.string.alert_message_edit_error))
        }
    }

    @Test
    fun `when save button clicked and is register, edit pet is called and loading is shown`() {
        runBlocking {
            val petView: PetView = mockPetView.copy(id = "")
            val pet = Mapper.mapPet(petView)
            whenever(registerPetUseCase.invoke(pet)).thenReturn(Result(Unit, null))
            vm.model.observeForever(observer)

            vm.onSavePet(petView)

            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.Loading(true))
            verify(registerPetUseCase).invoke(pet)
        }
    }

    @Test
    fun `when save button clicked and is register, call edit pet is correct`() {
        runBlocking {
            val petView: PetView = mockPetView.copy(id = "")
            val pet = Mapper.mapPet(petView)
            whenever(registerPetUseCase.invoke(pet)).thenReturn(Result(Unit, null))
            vm.model.observeForever(observer)

            vm.onSavePet(petView)

            verify(registerPetUseCase).invoke(pet)
            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.SuccessAdvice)
        }
    }

    @Test
    fun `when save button clicked and is register, call edit pet is incorrect`() {
        runBlocking {
            val petView: PetView = mockPetView.copy(id = "")
            val pet = Mapper.mapPet(petView)
            whenever(registerPetUseCase.invoke(pet)).thenReturn(Result(null, listOf(Error(UseCaseErrors.REGISTER_PET_GENERIC_ERROR))))
            vm.model.observeForever(observer)

            vm.onSavePet(petView)

            verify(registerPetUseCase).invoke(pet)
            verify(observer).onChanged(EditRegisterPetViewModel.UiModel.ErrorAdvice(R.string.alert_message_registration_error))
        }
    }
}