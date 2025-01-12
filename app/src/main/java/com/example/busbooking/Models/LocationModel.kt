package com.example.busbooking.Models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel(
    val location:LatLng
):Parcelable