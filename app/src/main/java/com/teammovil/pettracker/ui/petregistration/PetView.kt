package com.teammovil.pettracker.ui.petregistration

import androidx.annotation.StringRes
import com.teammovil.pettracker.R
import com.teammovil.pettracker.domain.*
import java.util.*

class PetView (
    var id: String?,
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

open class FieldView<T> (
        var value : T,
        var valid: Boolean = true,
        @StringRes var messageResourceId: Int = R.string.error_field_required
)

class SelectFieldView<T>(
        value: T,
        var selection: Int = 0
) : FieldView<T>(value)


