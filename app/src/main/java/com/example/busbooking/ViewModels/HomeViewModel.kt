package com.example.busbooking.ViewModels

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.Models.UserModel
import com.example.busbooking.Repositories.HomeRepo
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(private val repo:HomeRepo):ViewModel() {
    private val _user = MutableLiveData<UserModel?>()
    val user : MutableLiveData<UserModel?>
        get() = _user
    private val _routes = MutableLiveData<List<RouteModel>>()
    val routes : MutableLiveData<List<RouteModel>>
        get() = _routes
    val _governorate = MutableLiveData<String>()
    val governorate : LiveData<String>
        get() = _governorate



    fun getCurrentUser(userId:String){
        repo.getCurrentUser(userId){userDate->
            _user.value = userDate
        }
    }

    fun getAllRoutes(){
        repo.getAllRoutes { routesData->
            _routes.value = routesData
        }
    }

    fun getCurrentUserTravelsByLocation(fusedLocationProviderClient: FusedLocationProviderClient, geoCoder: Geocoder){
        repo.getCurrentUserTravelsByLocation(fusedLocationProviderClient , geoCoder){routesData,governorate->
            _routes.value = routesData
            _governorate.value = governorate
        }
    }
}