package com.teammovil.pettracker.ui.dewormings

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DewormingView(
    var id : Int = 0,
    var idExternal : String = "",
    var name : String,
    var applicationDate: String
):Parcelable