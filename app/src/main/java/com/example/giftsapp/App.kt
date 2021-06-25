package com.example.giftsapp

import android.app.Application
import com.google.firebase.auth.FirebaseAuth

class App : Application() {
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        var app : App? = null
    }

    fun getFirebaseAuth() : FirebaseAuth = firebaseAuth

    override fun onCreate() {
        super.onCreate()
        if (app == null) {
            app = this
            firebaseAuth = FirebaseAuth.getInstance()
        }
    }
}