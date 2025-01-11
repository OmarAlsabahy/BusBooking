package com.example.busbooking.Repositories

import com.example.busbooking.Models.RouteModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class SearchRepo @Inject constructor(private val database: FirebaseDatabase){

    fun getTravelByStartAndEndAndDate(start: String , end: String , date: String , callBack:(List<RouteModel>)->Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("start").equalTo(start.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children) {
                    val route = data.getValue(RouteModel::class.java)
                    if (route != null) {
                        if (route.end.trim().equals(end.trim())&&route.departureDate.trim().equals(date)) {
                            val todayFormated = SimpleDateFormat("dd-MM-yyyy").format(Date())
                           if (route.departureDate.trim().equals(todayFormated)){
                               list.add(route)
                           }
                        }
                    }
                    callBack(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getTravelByStart(start: String , callBack:(List<RouteModel>)->Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("start").equalTo(start.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children){
                    val route = data.getValue(RouteModel::class.java)
                    if (route != null) {
                        list.add(route)
                    }
                }
                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getTravelByStartAndDate(start:String , date:String,callBack: (List<RouteModel>) -> Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("start").equalTo(start.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children){
                    val route = data.getValue(RouteModel::class.java)
                    if (route!=null){
                        if (route.departureDate.trim().equals(date.trim())){
                            list.add(route)
                        }
                    }
                }
                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getTravelByEndAndDate(end:String , date: String,callBack: (List<RouteModel>) -> Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("end").equalTo(end.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children) {
                    val route = data.getValue(RouteModel::class.java)
                    if (route != null) {
                        if (route.departureDate.trim().equals(date.trim())) {
                            list.add(route)
                        }
                    }
                }

                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getTravelByEnd(end: String,callBack: (List<RouteModel>) -> Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("end").equalTo(end.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children) {
                    val route = data.getValue(RouteModel::class.java)
                    if (route != null) {
                        list.add(route)
                    }
                }
                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getTravelByDate(date:String,callBack: (List<RouteModel>) -> Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("departureDate").equalTo(date.trim()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children) {
                    val route = data.getValue(RouteModel::class.java)
                    if (route != null) {
                        list.add(route)
                    }
                }
                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    fun getTravelByStartAndEnd(start: String,end: String,callBack: (List<RouteModel>) -> Unit){
        val ref = database.getReference("Routes")
        ref.orderByChild("start").equalTo(start.trim()).addValueEventListener(object:ValueEventListener{
            val list = arrayListOf<RouteModel>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val route = data.getValue(RouteModel::class.java)
                    if (route != null) {
                        if (route.end.trim().equals(end.trim())) {
                            list.add(route)
                        }
                    }
                }
                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}