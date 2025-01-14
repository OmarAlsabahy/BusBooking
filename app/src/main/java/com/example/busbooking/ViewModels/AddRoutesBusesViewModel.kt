package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Repositories.AddRoutesBusesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRoutesBusesViewModel @Inject constructor(private val repo:AddRoutesBusesRepo):ViewModel() {
    private val _buses = MutableLiveData<List<BusModel>>()
    val buses : LiveData<List<BusModel>>
        get() = _buses
    fun getAllBuses(){
        repo.getAllBuses {busesList->
            _buses.value = busesList
        }

    }
}