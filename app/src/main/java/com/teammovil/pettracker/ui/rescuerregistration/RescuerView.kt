package com.teammovil.pettracker.ui.rescuerregistration

import com.teammovil.pettracker.ui.common.FieldView

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

