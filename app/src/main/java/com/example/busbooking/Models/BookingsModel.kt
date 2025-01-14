package com.example.busbooking.Models

data class BookingsModel (
    val bookingId:String="",
    val userId:String="",
    val busId:String="",
    val totalPrice:Float=0f,
    val startLocation:String="",
    val endLocation:String="",
    val date:String="",
    val time:String="",
    val travelTime:String="",
)

