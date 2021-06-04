package com.teammovil.domain


import com.teammovil.domain.rules.EvidenceValidator
import com.teammovil.domain.rules.RulesErrors
import com.teammovil.testshared.mockEvidence
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EvidenceTest {

    lateinit var evidenceValidator: EvidenceValidator

    @Before
    fun setUp() {
        evidenceValidator = EvidenceValidator
    }


    @Test
    fun `EvidenceValidator empty Image`() {
        val evidence = mockEvidence.copy(media = "")

        val result = evidenceValidator.validate(evidence).error?.get(0)?.code == RulesErrors.EVIDENCE_PHOTO_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `EvidenceValidator empty Comments`() {
        val evidence = mockEvidence.copy(comment = "")

        val result = evidenceValidator.validate(evidence).error?.get(0)?.code == RulesErrors.EVIDENCE_COMMENTS_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `EvidenceValidator empty Date`() {
        val evidence = mockEvidence.copy(date = null)

        val result = evidenceValidator.validate(evidence).error?.get(0)?.code == RulesErrors.EVIDENCE_DATE_FIELD_EMPTY_ERROR

        assertTrue(result)
    }

    @Test
    fun `EvidenceValidator success`() {

        val result = evidenceValidator.validate(mockEvidence).valid

        assertTrue(result)
    }
}