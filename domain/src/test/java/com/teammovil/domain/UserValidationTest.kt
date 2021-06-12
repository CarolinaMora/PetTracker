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
    fun `is valid Adopter email and password`() {
        val result = userValidator.validateUser(
            mockAdopter.email,
            mockAdopter.password)
            .valid

        assertTrue(result)
    }

    @Test
    fun `is valid Rescuer email and password`(){
        val result = userValidator.validateUser(
            mockRescuer.email,
            mockRescuer.password)
            .valid

        assertTrue(result)
    }

    @Test
    fun `is empty input email and password Adopter Error`() {
        val email = mockAdopter.copy(email = "")
        val password = mockAdopter.copy(password = "")
        val resultEmail = userValidator.validateUser(email.email, password.password).error?.get(0)?.code == RulesErrors.EMAIL_FIELD_EMPTY_ERROR
        val resultPassword = userValidator.validateUser(email.email, password.password).error?.get(0)?.code == RulesErrors.PASSWORD_FIELD_EMPTY_ERROR

        assertTrue(resultEmail)
        assertTrue(resultPassword)

    }

    @Test
    fun `is empty input email and password Rescuer Error`() {
        val email = mockRescuer.copy(email = "")
        val password = mockRescuer.copy(password = "")
        val resultEmail = userValidator.validateUser(email.email, password.password).error?.get(0)?.code == RulesErrors.EMAIL_FIELD_EMPTY_ERROR
        val resultPassword = userValidator.validateUser(email.email, password.password).error?.get(0)?.code == RulesErrors.PASSWORD_FIELD_EMPTY_ERROR

        assertTrue(resultEmail)
        assertTrue(resultPassword)

    }

}