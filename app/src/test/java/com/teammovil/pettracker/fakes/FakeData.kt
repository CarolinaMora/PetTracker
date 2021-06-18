package com.teammovil.pettracker.fakes

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.pettracker.ui.adopterregistration.AdopterRegistrationViewModel
import com.teammovil.pettracker.ui.editregisterpet.EditRegisterPetViewModel
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
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

    val fakeRegisterAdopterUseCase = RegisterAdopterUseCase(fakeAdopterRepository)

    //View Models
    val fakeEditPetViewModel = EditRegisterPetViewModel(
        fakeEditPetUseCase,
        fakeGetDetailUseCase,
        fakeRegisterPetUseCase,
        Dispatchers.Unconfined
    )

    val fakeAdopterRegistrationViewModel = AdopterRegistrationViewModel(
        fakeRegisterAdopterUseCase,
        Dispatchers.Unconfined
    )

}