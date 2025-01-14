package com.example.busbooking.Repositories

import com.example.busbooking.Models.BusModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class AddRoutesBusesRepo @Inject constructor(private val database: FirebaseDatabase) {
    fun getAllBuses(callBack:(List<BusModel>)->Unit){
        val ref = database.getReference("Buses")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val buses = arrayListOf<BusModel>()
                for (data in snapshot.children){
                    val bus = data.getValue(BusModel::class.java)
                    if (bus!=null){
                        buses.add(bus)
                    }
                }
                callBack(buses)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}