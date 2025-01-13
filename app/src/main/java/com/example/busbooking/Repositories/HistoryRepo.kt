package com.example.busbooking.Repositories

import com.example.busbooking.Models.Bookings
import com.example.busbooking.Models.BusModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class HistoryRepo @Inject constructor(private val database: FirebaseDatabase) {
    fun getAllUserTicket(userId:String,callBack:(ArrayList<Bookings>)->Unit){
        val bookingRef = database.getReference("Bookings")
        bookingRef.orderByChild("userId").equalTo(userId.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookings = arrayListOf<Bookings>()
                for (data in snapshot.children){
                    val booking = data.getValue(Bookings::class.java)
                    if (booking != null) {
                        bookings.add(booking)
                    }
                }
                callBack(bookings)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getBusNumber(busId:String, callBack:(String)->Unit){
        val busRef = database.getReference("Buses")
        var busNumber = ""
        busRef.orderByChild("busId").equalTo(busId.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bus = snapshot.children.firstOrNull()?.getValue(BusModel::class.java)
                if (bus != null) {
                    busNumber = bus.busNumber
                    callBack(busNumber)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}