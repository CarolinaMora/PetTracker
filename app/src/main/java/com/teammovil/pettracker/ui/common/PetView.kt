package com.teammovil.pettracker.ui.common

import com.teammovil.domain.Deworming
import com.teammovil.domain.Evidence
import com.teammovil.domain.PetStatus
import com.teammovil.domain.Vaccine

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
    var vaccines: FieldView<List<com.teammovil.domain.Vaccine>?>,
    var dewormings: FieldView<List<com.teammovil.domain.Deworming>?>,
    var mainPhoto: FieldView<String?>,
    var status: FieldView<com.teammovil.domain.PetStatus>,
    var evidences: FieldView<List<com.teammovil.domain.Evidence>?>
)




