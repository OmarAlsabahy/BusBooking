package com.example.busbooking.Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RouteModel(
    val routeId:String="",
    val start:String="",
    val end:String="",
    val stops:List<String> = listOf(""),
    val departureTime:String="",
    val departureDate:String="",
    val arrivalTime:String="",
    val price:Float=0F,
    val imageUrl:String="",
    val travelTime:String=""
):Parcelable