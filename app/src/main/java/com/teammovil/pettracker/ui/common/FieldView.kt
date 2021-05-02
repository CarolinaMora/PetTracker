package com.teammovil.pettracker.ui.common

import androidx.annotation.StringRes
import com.teammovil.pettracker.R

open class FieldView<T> (
    var value: T,
    var valid: Boolean = true,
    @StringRes var messageResourceId: Int = R.string.error_field_required
)

class SelectFieldView<T>(
    value: T,
    var selection: Int = 0
) : FieldView<T>(value)