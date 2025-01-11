package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Models.UserModel
import com.example.busbooking.Repositories.SignUpRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class SignUpViewModel @Inject constructor(private val repo: SignUpRepo):ViewModel() {
    private val _signUpStatus = MutableLiveData<Boolean>()
    val signUpStatus: MutableLiveData<Boolean>
        get() = _signUpStatus
    val _addUserStatus = MutableLiveData<Boolean>()
        val addUserStatus: LiveData<Boolean>
        get() = _addUserStatus
    fun signUp(email:String,password:String){
        repo.signUp(email,password){status->
            if (status){
                _signUpStatus.value = true
            }else{
                _signUpStatus.value = false
            }
        }
    }

   fun addUser(user: UserModel){
        repo.addUser(user){status->
            _addUserStatus.value = true
        }
    }
}