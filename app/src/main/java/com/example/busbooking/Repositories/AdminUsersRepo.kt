package com.example.busbooking.Repositories

import com.example.busbooking.Models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class AdminUsersRepo @Inject constructor(private val database:FirebaseDatabase) {
    fun getAllUsers(callBack:(List<UserModel>)->Unit){
        val userRef = database.getReference("Users")
        userRef.addValueEventListener(object:ValueEventListener{
            val usersList = arrayListOf<UserModel>()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val user = data.getValue(UserModel::class.java)
                    if (user!=null){
                        usersList.add(user)
                    }
                }
                callBack(usersList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}