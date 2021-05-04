package com.teammovil.pettracker.ui.sendevidence

import com.teammovil.pettracker.ui.common.FieldView

class EvidenceView (
    var externalId : String,
    var photo: FieldView<String?>,
    var comments: FieldView<String?>,
    var evidenceDate: FieldView<String?>)
