package com.teammovil.pettracker.ui.vaccines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class VaccineView(
    var id : Int = 0,
    var idExternal : String = "",
    var name : String,
    var applicationDate: String
):Parcelable