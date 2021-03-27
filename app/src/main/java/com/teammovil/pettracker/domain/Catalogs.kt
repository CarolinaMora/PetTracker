package com.teammovil.pettracker.domain

enum class PetType (val type: String) {
    DOG("Perro"),
    CAT("Gato"),
    FISH("Pez"),
    BIRD("Ave"),
    RABBIT("Conejo"),
    REPTILE("Reptil"),
    OTHER("Otro")
}

enum class GenderType (val gender: String) {
    MALE("Masculino"),
    FEMALE("Femenino")
}

enum class PetStatus {
    RESCUED,
    IN_ADOPTION,
    ADOPTED
}