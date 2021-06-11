package com.teammovil.domain

import com.teammovil.domain.rules.RulesErrors
import com.teammovil.domain.rules.adoptervalidator.AdopterValidator
import com.teammovil.domain.rules.adoptervalidator.AdopterValidatorImp
import com.teammovil.testshared.mockAdopter
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AdopterValidationTest {

    lateinit var adopterValidator: AdopterValidator

    @Before
    fun setUp(){
        adopterValidator = AdopterValidatorImp()
    }

    @Test
    fun `AdopterValidator empty adopter name invalid` (){
        //GIVEN
        val adopter = mockAdopter.copy(name = "")

        //WHEN
        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.NAME_FIELD_EMPTY_ERROR

        //THEN
        assertTrue(result)
    }

    @Test
    fun `AdopterValidator empty adopter first last name invalid`(){
        val adopter = mockAdopter.copy(firstLastName = "")

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.FIRST_LAST_NAME_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator empty adopter second last name invalid`(){
        val adopter = mockAdopter.copy(secondLastName = "")

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.SECOND_LAST_NAME_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator unknown adopter gender invalid`(){
        val adopter = mockAdopter.copy(gender = GenderType.UNKNOWN)

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.GENDER_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator empty adopter birth date date invalid`(){
        val adopter = mockAdopter.copy(BirthDate = null)

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.BIRTH_DATE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator empty adopter password invalid`(){
        val adopter = mockAdopter.copy(password = "")

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.PASSWORD_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator empty adopter phone invalid`(){
        val adopter = mockAdopter.copy(phone = "")

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.PHONE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator empty adopter address invalid`(){
        val adopter = mockAdopter.copy(address = "")

        val result = adopterValidator.validateAdopter(adopter).error?.get(0)?.code == RulesErrors.ADDRESS_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator adopter valid`(){
        val adopter = mockAdopter.copy()

        val result = adopterValidator.validateAdopter(adopter).success != null

        assertTrue(result)
    }

    @Test
    fun `AdopterValidator adopter invalid`(){
        val adopter = mockAdopter.copy()

        val result = adopterValidator.validateAdopter(adopter).success == null

        assertFalse(result)
    }

}