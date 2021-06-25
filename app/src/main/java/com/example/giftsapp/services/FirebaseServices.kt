package com.example.giftsapp.services

import android.app.Activity
import com.example.giftsapp.App
import com.example.giftsapp.tools.showToastMessage

object FirebaseServices {
    fun createNewUser(activity : Activity, email: String, password: String, userIsCreated : (Boolean) -> Unit) {
        App.app!!.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                activity
            ) { task ->
                if (task.isSuccessful) {
                    userIsCreated(true)
                    showToastMessage("Account has been created successfully")
                } else {
                    showToastMessage(task.exception?.message!!)
                }
            }
    }

    fun signInUser(activity: Activity,email : String, password : String, isUserSignedIn : (Boolean) -> Unit) {
        App.app!!.getFirebaseAuth().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    showToastMessage("Signed In Successfully")
                    isUserSignedIn(true)
                } else {
                    showToastMessage(task.exception?.message!!)
                }
            }
    }
}