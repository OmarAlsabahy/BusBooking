package com.example.busbooking.Repositories

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.busbooking.Models.Bookings
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class BookingRepo @Inject constructor(private val database: FirebaseDatabase) {
    @Inject
    lateinit var auth: FirebaseAuth
    fun bookTicket(routeModel: RouteModel, numberOfTickets: Int , callBack:(Boolean)->Unit) {
        val bookingRef = database.getReference("Bookings")
        val userId = auth.currentUser?.uid
            if (isAvailable(routeModel,numberOfTickets)) {
                val busId = routeModel.busId
               for (i in 1..numberOfTickets){
                   val bookingId = bookingRef.push().key
                   val booking = Bookings(
                       bookingId = bookingId!!,
                       busId = busId,
                       userId = userId!!,
                       totalPrice = routeModel.price,
                        date = routeModel.departureDate,
                       time = routeModel.departureTime,
                       startLocation = routeModel.start,
                       endLocation = routeModel.end,
                       travelTime = routeModel.travelTime
                   )
                   bookingRef.child(bookingId).setValue(booking)
               }
                updateRouteData(routeModel, numberOfTickets)
                callBack(true)
            }else{
                callBack(false)
            }
        }

    private fun isAvailable(routeModel: RouteModel,numberOfTickets: Int):Boolean{
        if (routeModel.availableSeats >= numberOfTickets){
            return true
        }else{
            return false
        }
    }

    private fun updateRouteData(routeModel: RouteModel, numberOfTickets: Int) {
        val routeRef = database.getReference("Routes")
        val availableSeats = routeModel.availableSeats - numberOfTickets
        routeRef.child(routeModel.routeId).child("availableSeats").setValue(availableSeats)
    }


    fun sendNotification(context: Context) {
        val builder = NotificationCompat.Builder(context , "App_Channel")
            .setContentTitle("Bus Booking")
            .setContentText("Your Ticket Has Been Booked")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1,builder.build())
    }
}