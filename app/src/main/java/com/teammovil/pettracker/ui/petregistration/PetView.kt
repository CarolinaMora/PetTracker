package com.teammovil.pettracker.ui.petregistration

import com.teammovil.pettracker.domain.*
import java.util.*

class PetView (
    var id: String?,
    var name: FieldView<String?>,
    var gender: FieldView<String?>,
    var race:FieldView< String?>,
    var description: FieldView<String?>,
    var approximateDateOfBirth: FieldView<String?>,
    var rescueDate:FieldView< String?>,
    var petType: FieldView<String?>,
    var sterilized: FieldView<Boolean>,
    var vaccines: FieldView<List<Vaccine>?>,
    var dewormings: FieldView<List<Date>?>,
    var mainPhoto: FieldView<String?>,
    var status: FieldView<PetStatus>,
    var evidences: FieldView<List<Evidence>?>
)

class FieldView<T> (
        var value : T,
        var valid: Boolean = true,
        var message: String = ""
)


