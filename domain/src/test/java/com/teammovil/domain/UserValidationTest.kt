package com.teammovil.domain

import com.teammovil.domain.rules.RulesErrors
import com.teammovil.domain.rules.UserValidator
import com.teammovil.domain.rules.UserValidatorImpl
import com.teammovil.testshared.mockAdopter
import com.teammovil.testshared.mockRescuer
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserValidationTest {

    lateinit var userValidator: UserValidator


    @Before
    fun setUp(){
        userValidator = UserValidatorImpl()
    }

    @Test
    fun `is valid  email and password UserValidator`() {
        val result = userValidator.validateUser(
            mockAdopter.email,
            mockAdopter.password)
            .valid

        assertTrue(result)
    }


    @Test
    fun `empty input email UserValidator`() {
        val email = ""
        val password = mockAdopter.password
        val result = userValidator.validateUser(email, password).error?.get(0)?.code == RulesErrors.EMAIL_FIELD_EMPTY_ERROR
        assertTrue(result)

    }

    @Test
    fun `empty input password UserValidator`() {
        val email = mockRescuer.email
        val password = ""
        val result = userValidator.validateUser(email, password).error?.get(0)?.code == RulesErrors.PASSWORD_FIELD_EMPTY_ERROR
        assertTrue(result)

    }
}