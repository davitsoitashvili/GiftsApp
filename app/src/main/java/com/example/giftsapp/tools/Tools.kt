package com.example.giftsapp.tools

import android.widget.Toast
import com.example.giftsapp.App

fun showToastMessage(message: String) {
    Toast.makeText(App.app!!.applicationContext, message, Toast.LENGTH_LONG).show()
}
