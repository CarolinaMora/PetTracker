package com.teammovil.pettracker.fakes

import com.teammovil.domain.GenderType
import com.teammovil.domain.PetStatus
import com.teammovil.domain.PetType
import com.teammovil.pettracker.ui.adopterregistration.AdopterView
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.common.PetView
import com.teammovil.pettracker.ui.common.SelectFieldView
import com.teammovil.pettracker.ui.common.UserView
import com.teammovil.pettracker.ui.rescuerregistration.RescuerView
import com.teammovil.pettracker.ui.sendevidence.EvidenceView

val mockRescuerView = RescuerView(
    "1",
    name = FieldView("Jefferson Rescata"),
    descripion = FieldView("Rescatista independiente"),
    address = FieldView("Ecuador"),
    email = FieldView("jefferson@gmail.com"),
    password = FieldView("hola1234"),
    phone = FieldView("5566778899"),
    activityStartDate = FieldView("2020-11-02")
)

val mockAdopterView = AdopterView(
    "karen@gmail.com.mx",
    name = FieldView("Karen"),
    firstLastName = FieldView("Pérez"),
    secondLastName = FieldView("García"),
    genderType = FieldView(GenderType.MALE.name),
    birthDay = FieldView("1992-07-19"),
    email = FieldView("karen@gmail.com"),
    password = FieldView("hola1234"),
    phone = FieldView("5566778899"),
    address = FieldView("Ciudad de México")
)

val mockPetView = PetView(
    "3",
    name = FieldView("Guenu"),
    gender = SelectFieldView(GenderType.MALE.name),
    race = FieldView("Mestiza"),
    description = FieldView("Es un gato muy cariñoso y dócil, maúya mucho y le gusta estar afuera"),
    approximateDateOfBirth = FieldView("2020-10-11"),
    rescueDate = FieldView("2020-11-10"),
    petType = SelectFieldView(PetType.CAT.name),
    sterilized = FieldView(true),
    vaccines = FieldView(listOf()),
    dewormings = FieldView(listOf()),
    mainPhoto = FieldView("https://estaticos.muyinteresante.es/media/cache/400x400_thumb/uploads/images/gallery/59afbfa15bafe859daa6fbbc/gato-exotico.jpg"),
    status = FieldView(PetStatus.ADOPTED),
    evidences = FieldView(listOf())
)

val mockEvidenceView = EvidenceView(
    externalId = "1",
    photo = FieldView("image.png"),
    comments = FieldView("hola"),
    evidenceDate = FieldView("2021-06-09")
)

val mockUserView = UserView(
    email = FieldView("hola@adios.com"),
    password = FieldView("hola1234")
)