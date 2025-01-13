package com.example.busbooking.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.busbooking.Repositories.AdminRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val repo: AdminRepo) : ViewModel() {
    private val _types = MutableLiveData<List<String>>()
    val types: LiveData<List<String>>
        get() = _types
    fun getTablesTypes(){
       _types.value = repo.getTablesTypes()
    }

}