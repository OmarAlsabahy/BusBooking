package com.example.busbooking.Repositories

import com.example.busbooking.Models.RouteModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class AdminRoutesRepo @Inject constructor(private val database:FirebaseDatabase) {
    fun getAllRoutes(callBack:(List<RouteModel>)->Unit){
        val routeRef = database.getReference("Routes")
        routeRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val routes = arrayListOf<RouteModel>()
                for (data in snapshot.children){
                    val route = data.getValue(RouteModel::class.java)
                    if (route!=null){
                        routes.add(route)
                    }
                }
                callBack(routes)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}