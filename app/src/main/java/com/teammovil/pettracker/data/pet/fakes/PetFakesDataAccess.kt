package com.teammovil.pettracker.data.pet.fakes

import com.teammovil.pettracker.data.pet.PetExternalDataAccess
import com.teammovil.pettracker.domain.*
import com.teammovil.pettracker.getDateFromString
import java.util.*

val fakePetList: MutableList<Pet> = listOf(
    Pet(
        "1",
        "Amber",
        GenderType.FEMALE,
        "Mestiza",
        "Tiene actividad media, juega mucho pero también duerme mucho, no le gusta que la dejen sola",
        getDateFromString("2020-09-02")?.let{it}?: Date(),
        getDateFromString("2020-11-02")?.let{it}?: Date(),
        PetType.DOG,
        false,
        listOf(
            Vaccine("Puppy 1", getDateFromString("2020-11-20")?.let{it} ?: Date()),
            Vaccine("Puppy 2", getDateFromString("2020-12-20")?.let{it} ?: Date()),
            Vaccine("Rabia 1", getDateFromString("2020-12-20")?.let{it} ?: Date())
        ),
        listOf(
            getDateFromString("2020-11-20")?.let{it} ?: Date(),
            getDateFromString("2020-12-20")?.let{it} ?: Date()
        ),
        "https://d1pta9xlyeo3pg.cloudfront.net/wp-content/uploads/seguro_para_perros.png",
        PetStatus.ADOPTED.ordinal,
        listOf(
            Evidence("Su primer mes", "https://protectorasar.org/wp-content/uploads/2018/02/WhatsApp-Image-2017-11-22-at-20.50.23-1-400x400.jpeg", Date())
        )
    ),
    Pet(
        "2",
        "Moka",
        GenderType.FEMALE,
        "Siamés",
        "Es una gata que todo el tiempo quiere caricias, es asustadiza y nerviosa",
        getDateFromString("2018-02-02")?.let{it}?: Date(),
        getDateFromString("2020-02-02")?.let{it}?: Date(),
        PetType.CAT,
        false,
        listOf(),
        listOf(),
        "https://www.sudamerica.boehringer-ingelheim.com/sites/sudamerica/files/images/cat_ages_and_stages_kachel.jpg",
        PetStatus.RESCUED.ordinal,
        listOf()
    ),
    Pet(
        "3",
        "Guenu",
        GenderType.MALE,
        "Mestiza",
        "Es un gato muy cariñoso y dócil, maúya mucho y le gusta estar afuera",
        getDateFromString("2019-11-25")?.let{it}?: Date(),
        getDateFromString("2020-11-25")?.let{it}?: Date(),
        PetType.CAT,
        true,
        listOf(
            Vaccine("Tripe felina", getDateFromString("2020-12-20")?.let{it} ?: Date()),
            Vaccine("Rabia 1", getDateFromString("2020-12-20")?.let{it} ?: Date())
        ),
        listOf(
            getDateFromString("2020-12-20")?.let{it} ?: Date()
        ),
        "https://estaticos.muyinteresante.es/media/cache/400x400_thumb/uploads/images/gallery/59afbfa15bafe859daa6fbbc/gato-exotico.jpg",
        PetStatus.ADOPTED.ordinal,
        listOf()
    )
).toMutableList()


class PetFakeExternalDataAccess: PetExternalDataAccess{
    override suspend fun getAllPatsFromRescuer(rescuerId: String): List<Pet> {
        return fakePetList
    }

    override suspend fun getPetById(petId: String): Pet {
        val petFound = fakePetList.find { petId == it.id }
        return petFound?.let{it}?: fakePetList[0]
    }

    override suspend fun registerPet(pet: Pet): Boolean {
        return true
    }
}