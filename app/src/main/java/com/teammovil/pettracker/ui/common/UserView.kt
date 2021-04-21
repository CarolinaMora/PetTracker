package com.teammovil.pettracker.ui.common

class UserView(
    var email: FieldView<String?>,
    var password: FieldView<String?>
)

class FieldView<T> (
    var value: T,
    var valid: Boolean = true,
    var message: String = ""
)
