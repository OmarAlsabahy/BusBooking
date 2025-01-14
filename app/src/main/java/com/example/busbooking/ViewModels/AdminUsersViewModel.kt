package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.UserModel
import com.example.busbooking.Repositories.AdminUsersRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminUsersViewModel @Inject constructor(private val repo:AdminUsersRepo):ViewModel() {
    private val _users = MutableLiveData<List<UserModel>>()
    val users : LiveData<List<UserModel>>
        get() = _users
    fun getAllUsers(){
        repo.getAllUsers { usersList->
            _users.value = usersList
        }
    }
}