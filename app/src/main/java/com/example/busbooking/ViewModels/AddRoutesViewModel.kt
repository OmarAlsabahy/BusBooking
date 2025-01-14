package com.example.busbooking.ViewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.Repositories.AddRoutesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddRoutesViewModel @Inject constructor(private val repo: AddRoutesRepo) : ViewModel() {
    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded : LiveData<Boolean>
        get() = _isAdded
    private val _isSent = MutableLiveData<Boolean>()
    val isSent : LiveData<Boolean>
        get() = _isAdded
    fun addRoute(route:RouteModel){
        repo.addRoute(route){status->
            if (status){
                _isAdded.value = true
            }
        }
    }

    fun getRouteBusCapacity(busId:String,callBack:(Int)->Unit){
        repo.getRouteBusCapacity(busId){capacity->
            callBack(capacity)
        }
    }

    fun sendNotification(context: Context){
        repo.sendNotification(context)
        _isSent.value = true
    }
}