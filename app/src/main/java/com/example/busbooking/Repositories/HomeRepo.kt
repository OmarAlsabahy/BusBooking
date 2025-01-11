package com.example.busbooking.Repositories
import android.location.Geocoder
import android.os.Build
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.Models.UserModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class HomeRepo @Inject constructor(private val database : FirebaseDatabase) {
fun getCurrentUser(userId:String,callBack:(UserModel?)->Unit){
    database.getReference("Users").child(userId).addValueEventListener(object:ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val user = snapshot.getValue(UserModel::class.java)
            callBack(user)
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })
}

    fun getAllRoutes(callBack:(List<RouteModel>)->Unit){
        database.getReference("Routes").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = arrayListOf<RouteModel>()
                for (data in snapshot.children){
                    val route = data.getValue(RouteModel::class.java)
                    list.add(route!!)
                }
                callBack(list)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getCurrentUserTravelsByLocation(fusedLocationProviderClient: FusedLocationProviderClient , geoCoder:Geocoder,callBack: (List<RouteModel> , String) -> Unit){
        getUserLocation(fusedLocationProviderClient , geoCoder){governorate->
            val ref = database.getReference("Routes")
            ref.orderByChild("start").equalTo(governorate).addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = arrayListOf<RouteModel>()
                    for (data in snapshot.children){
                        val route = data.getValue(RouteModel::class.java)
                        list.add(route!!)
                    }
                    callBack(list , governorate)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }




    private fun getUserLocation(fusedLocationProviderClient: FusedLocationProviderClient , geoCoder: Geocoder , callBack: (String) -> Unit) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location->
            if (location!=null){
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                    geoCoder.getFromLocation(location.latitude , location.longitude,1,Geocoder.GeocodeListener { address->
                        val adminArea = address[0].adminArea
                        val governorate = adminArea.split(" ")[0]
                        callBack(governorate)
                    })
                }else{
                    val address = geoCoder.getFromLocation(location.latitude , location.longitude,1)
                    val AdminArea = address!![0].adminArea
                    val governorate = AdminArea.split(" ")[0]
                    callBack(governorate)
                }
            }
        }
    }

}