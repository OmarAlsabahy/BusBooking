package com.example.busbooking.ViewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.BusModel
import com.example.busbooking.Repositories.AddBusesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddBusesViewModel @Inject constructor(private val repo : AddBusesRepo) : ViewModel() {
    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded : LiveData<Boolean>
        get() = _isAdded
    private val _isSent = MutableLiveData<Boolean>()
    val isSent : LiveData<Boolean>
        get() = _isSent
    fun addBus(busModel: BusModel){
        repo.addBus(busModel){status->
            if (status){
                _isAdded.value = true
            }
        }
    }

    fun sendNotification(message:String , context:Context){
        repo.sendNotification(message , context){status->
            if (status){
                _isSent.value = true
            }
        }
    }
}