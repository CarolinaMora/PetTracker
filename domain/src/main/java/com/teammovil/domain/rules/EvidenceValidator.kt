package com.teammovil.domain.rules

import com.teammovil.domain.Error
import com.teammovil.domain.Evidence
import com.teammovil.domain.Result

object EvidenceValidator {

    fun validate(evidence: Evidence): Result<Unit, List<Error>> {
        val errorList = mutableListOf<Error>()

        if (evidence.media.isEmpty()) {
            errorList.add(Error(RulesErrors.EVIDENCE_PHOTO_FIELD_EMPTY_ERROR))
        }

        if (evidence.date == null) {
            errorList.add(Error(RulesErrors.EVIDENCE_DATE_FIELD_EMPTY_ERROR))
        }

        if (evidence.comment!!.isEmpty()){
            errorList.add(Error(RulesErrors.EVIDENCE_COMMENTS_FIELD_EMPTY_ERROR))
        }

        return if(errorList.isEmpty())
            Result(Unit, null)
        else
            Result(null, errorList)
    }

}
