package com.example.busbooking.ViewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.Repositories.BookingRepo
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class BookingViewModel @Inject constructor(private val repo:BookingRepo):ViewModel() {
    private val _fare = MutableLiveData<Float>()
    val fare : LiveData<Float>
        get() = _fare
    private val _youPay = MutableLiveData<Float>()
    val youPay : LiveData<Float>
        get() = _youPay
    private val _seat = MutableLiveData<Int>()
    val seat : LiveData<Int>
        get() = _seat
    private val _isBooked = MutableLiveData<Boolean>()
    val isBooked : LiveData<Boolean>
        get() = _isBooked
    fun setData(routeModel:RouteModel){
        _fare.value = routeModel.price
        _youPay.value = routeModel.price
        _seat.value = 1
    }

    fun addSeat(){
        _youPay.value = _youPay.value?.plus(_fare.value!!)
        _seat.value = _seat.value?.plus(1)
    }
    fun minSeat(){
        _youPay.value = _youPay.value?.minus(_fare.value!!)
        _seat.value = _seat.value?.minus(1)
    }

    fun book(routeModel: RouteModel,numberOfTickets:Int){
        repo.bookTicket(routeModel,numberOfTickets){isBooked->
            if (isBooked){
                _isBooked.value = true
            }else{
                _isBooked.value = false
            }
        }
    }

    fun sendNotification(context: Context){
        repo.sendNotification(context)
    }
}