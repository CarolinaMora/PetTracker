package com.teammovil.domain

import java.util.*

data class Rescuer (
    var id: String,
    var name: String,
    var description: String,
    var address: String?,
    var email: String,
    var password: String,
    var phone: String,
    var activityStartDate: Date

)