package com.teammovil.domain

import com.teammovil.domain.rules.RescuerValidator
import com.teammovil.domain.rules.RulesErrors
import com.teammovil.testshared.mockRescuer
import org.junit.Assert.assertTrue
import org.junit.Test

class RescuerValidatorTest {

    @Test
    fun `is valid rescuer in RescuerValidator`(){
        val rescuer = mockRescuer.copy()
        val result = RescuerValidator.validateRescuer(rescuer).valid

        assertTrue(result)
    }

    @Test
    fun `empty input name RescuerValidator`(){
        val rescuer = mockRescuer.copy(name = "")
        val result = RescuerValidator.validateRescuer(rescuer).error?.get(0)?.code == RulesErrors.NAME_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `empty activityStartDate in RescuerValidator`(){
        val rescuer = mockRescuer.copy(activityStartDate = null)
        val result = RescuerValidator.validateRescuer(rescuer).error?.get(0)?.code == RulesErrors.ACTIVITY_START_DATE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }
}