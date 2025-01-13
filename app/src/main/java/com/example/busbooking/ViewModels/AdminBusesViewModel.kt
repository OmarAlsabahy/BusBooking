package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Repositories.AdminBusesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminBusesViewModel @Inject constructor(private val repo: AdminBusesRepo) : ViewModel() {
    private val _buses = MutableLiveData<List<BusModel>>()
    val buses : LiveData<List<BusModel>>
        get() = _buses
    fun getAllBuses(){
        repo.getAllBuses { buses->
            _buses.value = buses
        }
    }
}