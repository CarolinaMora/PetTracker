package com.teammovil.pettracker.ui.rescuerregistration

class RescuerView(
    var id: String?,
    var name: FieldView<String?>,
    var descripion: FieldView<String?>,
    var address: FieldView<String?>,
    var email: FieldView<String?>,
    var password: FieldView<String?>,
    var phone: FieldView<String?>,
    var activityStartDate: FieldView<String?>
)

class FieldView<T>(
    var value: T,
    var valid: Boolean = true,
    var message: String = ""
)