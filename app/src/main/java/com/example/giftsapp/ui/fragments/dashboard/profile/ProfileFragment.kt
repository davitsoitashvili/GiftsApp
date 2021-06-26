package com.example.giftsapp.ui.fragments.dashboard.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentProfileBinding

class ProfileFragment(val isSignedOut : (Boolean) -> Unit) : Fragment(R.layout.fragment_profile) {
    private lateinit var binding : FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        signOut()
    }

    private fun signOut() {
        binding.signOutBtn.setOnClickListener {
            App.app!!.getFirebaseAuth().signOut()
            isSignedOut(true)
        }
    }
}