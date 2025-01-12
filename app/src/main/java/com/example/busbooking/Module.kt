package com.example.busbooking

import android.app.Application
import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabaserefrence():DatabaseReference {
        val database = Firebase.database
        val ref = database.reference
        return ref
    }


    @Provides
    @Singleton
    fun provideFirebaseDatabase():FirebaseDatabase{
        return Firebase.database
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application:Application):Context{
        return application.applicationContext
    }

}