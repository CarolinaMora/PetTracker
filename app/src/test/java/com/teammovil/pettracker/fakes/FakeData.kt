package com.teammovil.pettracker.fakes

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Evidence
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.adopterpets.AdopterPetsViewModel
import com.teammovil.pettracker.ui.assigningadoptertopet.AdopterViewModel
import com.teammovil.pettracker.ui.adopterregistration.AdopterRegistrationViewModel
import com.teammovil.pettracker.ui.editregisterpet.EditRegisterPetViewModel
import com.teammovil.pettracker.ui.sendevidence.EvidenceView
import com.teammovil.pettracker.ui.sendevidence.SendEvidenceViewModel
import com.teammovil.pettracker.ui.rescuerregistration.RescuerRegistrationViewModel
import com.teammovil.pettracker.ui.registeredpets.RegisteredPetsViewModel
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registeradopter.RegisterAdopterUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import com.teammovil.usecases.SaveEvidenceUseCase
import com.teammovil.usecases.registerrescuer.RegisterRescuerUseCase
import com.teammovil.usecases.rescuerPets.GetRescuerPets
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

    val fakeSaveEvidenceUseCase = SaveEvidenceUseCase(fakePetRepository)

    val fakeRegisterRescuerUseCase = RegisterRescuerUseCase(fakeRescuerRepository)

    val fakerescuerPetsUseCase = GetRescuerPets(fakePetRepository, fakeRescuerRepository)

    val fakeAssignAdopterToPetUseCase = AssignAdopterToPetUseCase(fakePetRepository)

    val fakeGatAllAdoptersUseCase = GetAllAdoptersUseCase(fakeAdopterRepository)

    val fakeRegisterAdopterUseCase = RegisterAdopterUseCase(fakeAdopterRepository)

    val fakeAdopterPetsUseCase = GetAdopterPetsUseCase(fakePetRepository, fakeAdopterRepository)

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

    val fakeSendEvidenceViewModel = SendEvidenceViewModel (
        fakeSaveEvidenceUseCase,
        Dispatchers.Unconfined
            )

    //Objects
    val fakeEvidenceView = EvidenceView(
        externalId = "1",
        photo = FieldView("image.png"),
        comments = FieldView("hola"),
        evidenceDate = FieldView("2021-06-09")
    )

    val fakeAssignAdopterToPetViewModel = AdopterViewModel(
        fakeAssignAdopterToPetUseCase,
        fakeGatAllAdoptersUseCase,
        Dispatchers.Unconfined
    )

    val fakeAdopterRegistrationViewModel = AdopterRegistrationViewModel(
        fakeRegisterAdopterUseCase,
        Dispatchers.Unconfined
    )

    val fakeregisteredpetsViewModel = RegisteredPetsViewModel(
        fakerescuerPetsUseCase, Dispatchers.Unconfined
    )
    val fakeadopterPetsViewModel = AdopterPetsViewModel(
        fakeAdopterPetsUseCase, Dispatchers.Unconfined
    )

}