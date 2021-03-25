package com.teammovil.pettracker.domain

data class PetsList (val name: String, val description: String, val image: String)

fun getPets(): List<PetsList> = listOf(
    PetsList("Max","Perro muy cariñoso y juguetón con personas.","https://loremflickr.com/320/240/dog"),
    PetsList("Milo","Perro muy cariñoso, juguetón y amigable.","https://loremflickr.com/320/240/dog"),
    PetsList("Toby","Ideal compañero para una familia joven que realice ejercicio.","https://loremflickr.com/320/240/dog"),
    PetsList("Jack","Perro muy cariñoso y juguetón con personas.","https://loremflickr.com/320/240/dog"),
    PetsList("Molly","Perro muy cariñoso, juguetón y amigable.","https://loremflickr.com/320/240/dog"),
    PetsList("Ruby","Ideal compañero para una familia joven que realice ejercicio.","https://loremflickr.com/320/240/dog")

)