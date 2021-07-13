package com.teammovil.domain

import com.teammovil.domain.rules.PetValidator
import com.teammovil.domain.rules.PetValidatorImpl
import com.teammovil.domain.rules.RulesErrors
import com.teammovil.testshared.mockPet
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class PetValidationTest {

    lateinit var petValidator: PetValidator

    @Before
    fun setUp (){
        petValidator = PetValidatorImpl()
    }

    @Test
    fun `PetValidator empty pet name invalid` (){
        val pet = mockPet.copy(name="")

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.NAME_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator empty pet description invalid` (){
        val pet = mockPet.copy(description= "")

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.DESCRIPTION_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator empty pet race invalid` (){
        val pet = mockPet.copy(race="")

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.RACE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator unknown pet gender invalid` (){
        val pet = mockPet.copy(gender=GenderType.UNKNOWN)

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.GENDER_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator unknown pet type invalid` (){
        val pet = mockPet.copy(petType= PetType.UNKNOWN)

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.TYPE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator empty pet birth date invalid` (){
        val pet = mockPet.copy(approximateDateOfBirth = null)

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.BIRTH_DATE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator empty pet rescue date invalid` (){
        val pet = mockPet.copy(rescueDate = null)

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.RESCUE_DATE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator empty pet gender invalid` (){
        val pet = mockPet.copy(mainPhoto = "")

        val result = petValidator.validatePet(pet).error?.get(0)?.code == RulesErrors.MAIN_PHOTO_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `PetValidator pet valid` (){
        val pet = mockPet.copy()

        val result = petValidator.validatePet(pet).success != null

        assertTrue(result)
    }
}