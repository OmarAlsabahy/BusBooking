package com.example.busbooking.Repositories

import com.example.busbooking.Models.BookingsModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class AdminBookingRepo @Inject constructor(private val database:FirebaseDatabase) {
    fun getAllBookings(callBack:(List<BookingsModel>)->Unit){
        val bookingRef = database.getReference("Bookings")
        bookingRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookingList = arrayListOf<BookingsModel>()
                for (data in snapshot.children){
                    val booking = data.getValue(BookingsModel::class.java)
                    if (booking!=null){
                        bookingList.add(booking)
                    }
                }
                callBack(bookingList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}