package com.example.busbooking.Models

data class Bookings (
    val bookingId:String="",
    val userId:String="",
    val busId:String="",
    val numberOfTickets : Int =0,
    val totalPrice:Float=0f
)

