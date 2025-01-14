package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.BookingsModel
import com.example.busbooking.Repositories.AdminBookingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminBookingViewModel @Inject constructor(private val repo: AdminBookingRepo): ViewModel() {
    private val _bookings = MutableLiveData<List<BookingsModel>>()
    val bookings : LiveData<List<BookingsModel>>
        get() = _bookings

    fun getAllBookings(){
        repo.getAllBookings { bookingsList->
            _bookings.value = bookingsList
        }
    }
}