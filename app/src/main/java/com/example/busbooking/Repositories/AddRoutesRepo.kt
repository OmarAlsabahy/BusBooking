package com.example.busbooking.Repositories

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class AddRoutesRepo @Inject constructor(private val database: FirebaseDatabase) {
    fun addRoute(route:RouteModel,callBack:(Boolean)->Unit){
        val routeRef = database.getReference("Routes")
        val routeId = routeRef.push().key
        val routeModel = route.copy(routeId = routeId!!)
        routeRef.child(routeId).setValue(routeModel)
            .addOnSuccessListener {
                callBack(true)
            }.addOnFailureListener {
                callBack(false)
            }
    }

    fun getRouteBusCapacity(busId:String,callBack:(Int)->Unit){
        val busRef = database.getReference("Buses")
        busRef.orderByChild("busId").equalTo(busId.trim()).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bus = snapshot.children.firstOrNull()?.getValue(BusModel::class.java)
                if (bus!=null){
                    callBack(bus.capacity)
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
            .setContentText("Route Added Successfully")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1,builder.build())
    }
}