package com.example.busbooking.Repositories

import com.example.busbooking.Enums.Tables
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class AdminRepo @Inject constructor(private val database: FirebaseDatabase) {
    fun getTablesTypes():List<String>{
        return Tables.values().map {it.name}
    }
}