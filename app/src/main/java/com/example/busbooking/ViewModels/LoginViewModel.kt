package com.example.busbooking.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Repositories.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(private val repo:LoginRepo):ViewModel() {
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: MutableLiveData<Boolean>
        get() = _loginStatus
    fun login(email:String,password:String){
        repo.login(email,password){status->
            _loginStatus.value = status
        }
    }
}