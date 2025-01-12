package com.example.busbooking.Repositories

import android.app.NotificationManager
import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.busbooking.Models.Bookings
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.R
import com.google.android.gms.maps.model.LatLng
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
        var busId = ""
        val bookingRef = database.getReference("Bookings")
        val bookingId = bookingRef.push().key
        val userId = auth.currentUser?.uid


        findBus(routeModel, numberOfTickets) { busModel, isAvailable ->
            if (isAvailable) {
                val busId = busModel.busId
                val booking = Bookings(
                    bookingId = bookingId!!,
                    busId = busId,
                    userId = userId!!,
                    numberOfTickets = numberOfTickets,
                    totalPrice = numberOfTickets * routeModel.price
                )
                bookingRef.child(bookingId).setValue(booking)
                updateBusData(busModel, numberOfTickets)
                callBack(true)
            }else{
                callBack(false)
            }
        }
    }

    private fun updateBusData(busModel: BusModel, numberOfTickets: Int) {
        val busRef = database.getReference("Buses")
        val availableSeats = busModel.availableSeats - numberOfTickets
        busRef.child(busModel.busId).child("availableSeats").setValue(availableSeats)
    }

    private fun findBus(
        routeModel: RouteModel,
        numberOfTickets: Int,
        callBack: (BusModel, Boolean) -> Unit
    ) {
        val busRef = database.getReference("Buses")
        busRef.orderByChild("routeId").equalTo(routeModel.routeId.trim()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bus = snapshot.children.firstOrNull()?.getValue(BusModel::class.java)
                if (bus != null) {
                    if (bus.availableSeats>=numberOfTickets){
                        callBack(bus,true)
                    }else{
                        callBack(bus,false)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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