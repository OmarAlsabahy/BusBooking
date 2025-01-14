package com.example.busbooking.Repositories

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.busbooking.Models.BusModel
import com.example.busbooking.R
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class AddBusesRepo @Inject constructor(private val database : FirebaseDatabase) {
    fun addBus(busModel: BusModel,callBack:(Boolean)->Unit){
        val busRef = database.getReference("Buses")
        val busId = busRef.push().key
        val bus = busModel.copy(busId = busId!!)
        busRef.child(busId).setValue(bus).addOnSuccessListener {
            callBack(true)
        }.addOnFailureListener {
            callBack(false)
        }
    }
    fun sendNotification(message:String , context: Context,callBack: (Boolean) -> Unit){
        val builder = NotificationCompat.Builder(context , "App_Channel")
            .setContentTitle("Bus Booking")
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(1 , builder.build())
        callBack(true)
    }
}