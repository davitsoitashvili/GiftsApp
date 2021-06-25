package com.example.giftsapp.navigation.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment

fun Fragment.navigate(destination : Int) {
    NavHostFragment.findNavController(this).navigate(destination)
}