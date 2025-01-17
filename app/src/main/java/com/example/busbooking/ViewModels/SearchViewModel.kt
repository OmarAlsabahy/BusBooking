package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.Repositories.SearchRepo
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val repo:SearchRepo):ViewModel() {
    private val _searchedRoutes = MutableLiveData<List<RouteModel>>()
    val searchedRoutes : LiveData<List<RouteModel>>
        get() = _searchedRoutes


    fun getTravelByStart(start:String){
        repo.getTravelByStart(start){routes->
            _searchedRoutes.value = routes
        }
    }

    fun getTravelByStartAndDate(start:String,date:String){
        repo.getTravelByStartAndDate(start,date){routes->
            _searchedRoutes.value = routes
        }
    }

    fun getTravelByEndAndDate(end:String,date:String){
        repo.getTravelByEndAndDate(end,date){routes->
            _searchedRoutes.value = routes
        }

    }

    fun getTravelByEnd(end:String){
        repo.getTravelByEnd(end){routes->
            _searchedRoutes.value = routes
        }
    }

    fun getTravelByDate(date:String){
        repo.getTravelByDate(date){routes->
            _searchedRoutes.value = routes
        }

    }

    fun getTravelByStartAndEnd(start:String,end:String){
        repo.getTravelByStartAndEnd(start,end) { routes ->
            _searchedRoutes.value = routes
        }

    }

    fun getTravelByStartAndEndAndDate(start:String,end:String,date:String){
        repo.getTravelByStartAndEndAndDate(start,end,date){routes->
            _searchedRoutes.value = routes
        }
    }

    fun getLocationByName(name:String,callBack:(LatLng)->Unit){

        repo.getLocationLatLngByName(name){latLng->
            callBack(latLng)
        }
    }

    fun getBusByRouteId(busId:String,callBack:(BusModel)->Unit){
        repo.getBuById(busId){bus->
            callBack(bus)
        }
    }
}