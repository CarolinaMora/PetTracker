package com.teammovil.pettracker.ui.login

class AdopterView(
    var id: String?,
    var name: FieldView<String?>,
    var firstLastName: FieldView<String?>,
    var secondLastName: FieldView<String?>,
    var genderType: FieldView<String?>,
    var birthDay: FieldView<String?>,
    var email: FieldView<String?>,
    var password: FieldView<String?>,
    var phone: FieldView<String?>,
    var address: FieldView<String?>

)

class FieldView<T> (
    var value: T,
    var valid: Boolean = true,
    var message: String = ""

)
