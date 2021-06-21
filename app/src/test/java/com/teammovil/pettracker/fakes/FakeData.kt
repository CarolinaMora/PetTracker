package com.teammovil.pettracker.fakes

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.editregisterpet.EditRegisterPetViewModel
import com.teammovil.pettracker.ui.rescuerregistration.RescuerRegistrationViewModel
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import kotlinx.coroutines.Dispatchers


object FakeData {

    //Repositorios
    val fakePetRepository = PetRepository(PetFakeExternalDataAccess())

    val fakeRescuerRepository = RescuerRepository(RescuerFakeExternalDataAccess(), RescuerFakeStorageDataAccess())

    val fakeAdopterRepository = AdopterRepository(FakeAdopterExternalDataAccess(), FakeAdopterStorageDataAccess())

    //Casos de uso
    val fakeEditPetUseCase = EditPetUseCase(fakePetRepository)

    val fakeGetDetailUseCase = GetPetUseCase(fakePetRepository)

    val fakeRegisterPetUseCase = RegisterPetUseCase(fakeRescuerRepository, fakePetRepository)

    val fakeRegisterRescuerUseCase = RegisterRescuerUseCase(fakeRescuerRepository)

    //View Models
    val fakeEditPetViewModel = EditRegisterPetViewModel(
        fakeEditPetUseCase,
        fakeGetDetailUseCase,
        fakeRegisterPetUseCase,
        Dispatchers.Unconfined
    )

    val fakeRescuerRegistrationViewModel = RescuerRegistrationViewModel(
        fakeRegisterRescuerUseCase,
        Dispatchers.Unconfined
    )

}