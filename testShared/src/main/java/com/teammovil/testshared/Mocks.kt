package com.teammovil.testshared

import com.teammovil.domain.*
import java.util.*


val mockAdopter = Adopter(
    "karen@gmail.com",
    "Karen",
    "Pérez",
    "García",
    GenderType.FEMALE,
    Date(),
    "hola1234",
    "5566778899",
    "Ciudad de México"
)

val mockPet = Pet(
    "3",
    "Guenu",
    GenderType.MALE,
    "Mestiza",
    "Es un gato muy cariñoso y dócil, maúya mucho y le gusta estar afuera",
    Date(),
    Date(),
    PetType.CAT,
    true,
    listOf(
        Vaccine(
            "11",
            "Tripe felina",
            Date()),
        Vaccine(
            "12",
            "Rabia 1",
            Date())
    ),
    listOf(
        Deworming(
            "1",
            "",
            Date())
    ),
    "https://estaticos.muyinteresante.es/media/cache/400x400_thumb/uploads/images/gallery/59afbfa15bafe859daa6fbbc/gato-exotico.jpg",
    PetStatus.ADOPTED,
    listOf()
)

val mockRescuer = Rescuer(
    "1",
    "Jefferson Rescata",
    "Rescatista independiente",
    "Ecuador",
    "jefferson@gmail.com",
    "hola1234",
    "5566778899",
    Date()
)