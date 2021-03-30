package com.teammovil.pettracker.domain

import java.util.*

fun getPets(): List<Pet> = listOf(
        Pet("", "Max", GenderType.MALE, "", "Perro muy cariñoso y juguetón con personas.", Date(), Date(), PetType.DOG, false, listOf(), listOf(), "https://loremflickr.com/320/240/dog", 0, listOf()),
        Pet("", "Toby", GenderType.MALE, "", "Ideal compañero para una familia joven que realice ejercicio.", Date(), Date(), PetType.DOG, false, listOf(), listOf(), "https://loremflickr.com/320/240/dog", 0, listOf()),
        Pet("", "Ruby", GenderType.FEMALE, "", "Perro muy cariñoso, juguetón y amigable.", Date(), Date(), PetType.DOG, false, listOf(), listOf(), "https://loremflickr.com/320/240/dog", 0, listOf()),
        Pet("", "Molly", GenderType.FEMALE, "", "Ideal compañero para una familia joven que realice ejercicio.", Date(), Date(), PetType.DOG, false, listOf(), listOf(), "https://loremflickr.com/320/240/dog", 0, listOf()),
        Pet("", "Milo", GenderType.MALE, "", "Perro muy cariñoso y juguetón con personas.", Date(), Date(), PetType.DOG, false, listOf(), listOf(), "https://loremflickr.com/320/240/dog", 0, listOf()),
        Pet("", "Jack", GenderType.MALE, "", "Perro muy cariñoso, juguetón y amigable.", Date(), Date(), PetType.DOG, false, listOf(), listOf(), "https://loremflickr.com/320/240/dog", 0, listOf())

)