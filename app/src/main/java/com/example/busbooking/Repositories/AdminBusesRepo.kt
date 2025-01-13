package com.example.busbooking.Repositories

import com.example.busbooking.Models.BusModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class AdminBusesRepo @Inject constructor(private val database: FirebaseDatabase) {
    fun getAllBuses(callback:(buses:List<BusModel>)->Unit){
        val busRef = database.getReference("Buses")
        busRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val buses = arrayListOf<BusModel>()
                for (data in snapshot.children){
                    val bus = data.getValue(BusModel::class.java)
                    if (bus!=null){
                        buses.add(bus)
                    }
                }
                callback(buses)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}