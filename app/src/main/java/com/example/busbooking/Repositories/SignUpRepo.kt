package com.example.busbooking.Repositories

import com.example.busbooking.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

class SignUpRepo @Inject constructor(private val auth:FirebaseAuth , private val ref: DatabaseReference) {
    fun signUp(email:String,password:String , callBack:(Boolean)->Unit){
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener {task->
            if (task.isSuccessful){
                callBack(true)
            }else{
                callBack(false)
            }

        }
    }

    fun addUser(user:UserModel,callBack:(Boolean)->Unit){
        val userModel = user.copy(id = auth.currentUser!!.uid)
        ref.child("Users").child(auth.currentUser!!.uid).setValue(userModel).addOnCompleteListener{task->
            if (task.isSuccessful){
                callBack(true)
            }else{
                callBack(false)
            }
        }
    }
}