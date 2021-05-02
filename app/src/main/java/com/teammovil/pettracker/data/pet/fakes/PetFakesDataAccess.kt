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
            Vaccine("Rabia 1", getDateFromString("2020-10-01")?.let{it} ?: Date()),
            Vaccine("RTF", getDateFromString("2020-11-15")?.let{it} ?: Date()),
            Vaccine("Rabia 2", getDateFromString("2020-12-20")?.let{it} ?: Date())
        ),
        listOf(
            getDateFromString("2020-10-01")?.let{it} ?: Date(),
            getDateFromString("2020-12-20")?.let{it} ?: Date(),
            getDateFromString("2021-01-19")?.let{it} ?: Date(),
            getDateFromString("2021-03-05")?.let{it} ?: Date(),
            getDateFromString("2021-04-01")?.let{it} ?: Date()
        ),
        "https://d1pta9xlyeo3pg.cloudfront.net/wp-content/uploads/seguro_para_perros.png",
        PetStatus.ADOPTED,
        listOf(
            Evidence("Su primer mes", "https://previews.123rf.com/images/seagames50/seagames501510/seagames50151000818/46862428-labrador-puppy-cute-one-month-old.jpg", getDateFromString("2020-09-15")?.let{it} ?: Date()),
            Evidence("Su 2do mes", "https://i.pinimg.com/originals/5f/4b/83/5f4b83282d834386a8ef3414a5cc3fb0.jpg", getDateFromString("2020-10-15")?.let{it} ?: Date()),
            Evidence("Su 3er mes", "https://s.abcnews.com/images/US/puppy-zoey-ht-jpo-171027_16x9_992.jpg", getDateFromString("2020-11-17")?.let{it} ?: Date()),
            Evidence("Su 4 mes", "https://vetstreet-brightspot.s3.amazonaws.com/69/d9/c34f38d34bc1927d72b70738bf79/puppy-running-thinkstock-140262795-335sm2513.jpg", getDateFromString("2020-11-17")?.let{it} ?: Date()),
            Evidence("Su 5 mes", "data:image/jpeg;base64,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", getDateFromString("2020-11-17")?.let{it} ?: Date())
        ).toMutableList()
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
        PetStatus.RESCUED,
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
        PetStatus.ADOPTED,
        listOf()
    )
).toMutableList()


class PetFakeExternalDataAccess: PetExternalDataAccess{
    override suspend fun getAllPatsFromRescuer(rescuerId: String): List<Pet> {
        return fakePetList
    }

    override suspend fun getAllPetsFromAdopter(adopterId: String): List<Pet> {
        return fakePetList
    }

    override suspend fun getPetById(petId: String): Pet {
        Thread.sleep(5000)
        val petFound = fakePetList.find { petId == it.id }
        return petFound?.let{it}?: fakePetList[0]
    }

    override suspend fun registerPet(pet: Pet): Boolean {
        Thread.sleep(5000)
        fakePetList.add(pet)
        return true
    }

    override suspend fun updatePet(pet: Pet): Boolean {
        Thread.sleep(5000)
        fakePetList[pet.id.toInt()-1] = pet
        return true
    }

    override suspend fun saveEvidence(petId: String, evidence: Evidence): Boolean {
        val petFound = fakePetList.find { petId == it.id }
        if (petFound != null) {
            petFound.evidences[1].date = evidence.date
            petFound.evidences[1].comment = evidence.comment
            petFound.evidences[1].media = evidence.media
        }

        return true
    }

    override suspend fun assignAdopterToPet(petId: String, adopterId: String): Boolean {
        return true
    }
}