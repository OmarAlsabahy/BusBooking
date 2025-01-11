package com.example.busbooking.Repositories

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginRepo @Inject constructor(private val auth: FirebaseAuth) {
    fun login (email:String , password:String , callBack:(Boolean)->Unit){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            callBack(true)
        }.addOnFailureListener {
            callBack(false)

        }
    }
}