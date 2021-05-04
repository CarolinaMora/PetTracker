package com.teammovil.pettracker.ui.common

import com.teammovil.pettracker.domain.Evidence
import com.teammovil.pettracker.domain.PetStatus
import com.teammovil.pettracker.domain.Vaccine
import java.util.*

class PetView (
    var id: String = "",
    var name: FieldView<String?>,
    var gender: SelectFieldView<String?>,
    var race:FieldView< String?>,
    var description: FieldView<String?>,
    var approximateDateOfBirth: FieldView<String?>,
    var rescueDate:FieldView< String?>,
    var petType: SelectFieldView<String?>,
    var sterilized: FieldView<Boolean>,
    var vaccines: FieldView<List<Vaccine>?>,
    var dewormings: FieldView<List<Date>?>,
    var mainPhoto: FieldView<String?>,
    var status: FieldView<PetStatus>,
    var evidences: FieldView<List<Evidence>?>
)




