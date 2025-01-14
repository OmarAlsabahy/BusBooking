package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.Repositories.AdminRoutesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminRoutesViewModel @Inject constructor(private val repo: AdminRoutesRepo) : ViewModel() {
    private val _routes = MutableLiveData<List<RouteModel>>()
    val routes : LiveData<List<RouteModel>>
        get() = _routes
    fun getAllRoutes(){
        repo.getAllRoutes { routesList->
            _routes.value = routesList
        }
    }
}