package com.reyesmagos.squarefinder.core.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ViewVenue(
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
) : Parcelable
