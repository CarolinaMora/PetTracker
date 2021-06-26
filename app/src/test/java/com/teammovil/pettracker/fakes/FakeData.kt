package com.teammovil.pettracker.fakes

import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Evidence
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.editregisterpet.EditRegisterPetViewModel
import com.teammovil.pettracker.ui.sendevidence.EvidenceView
import com.teammovil.pettracker.ui.sendevidence.SendEvidenceViewModel
import com.teammovil.usecases.editpet.EditPetUseCase
import com.teammovil.usecases.petdetail.GetPetUseCase
import com.teammovil.usecases.registerpet.RegisterPetUseCase
import com.teammovil.usecases.SaveEvidenceUseCase
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

    //View Models
    val fakeEditPetViewModel = EditRegisterPetViewModel(
        fakeEditPetUseCase,
        fakeGetDetailUseCase,
        fakeRegisterPetUseCase,
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

}