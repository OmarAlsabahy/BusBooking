package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.Bookings
import com.example.busbooking.Repositories.HistoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repo:HistoryRepo): ViewModel() {
    private val _ticket = MutableLiveData<List<Bookings>>()
    val ticket : LiveData<List<Bookings>>
        get() = _ticket

    fun getUserTickets(userId:String){
        repo.getAllUserTicket(userId){userTickets->
            _ticket.value = userTickets
        }
    }

    fun getBusNumberByBusId(busId:String,callBack:(String)->Unit){
        repo.getBusNumber(busId){ busNumber->
            callBack(busNumber)
        }
    }


}