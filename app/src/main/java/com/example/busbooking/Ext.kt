package com.example.busbooking

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Context.showToast(context: Context, message:String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}