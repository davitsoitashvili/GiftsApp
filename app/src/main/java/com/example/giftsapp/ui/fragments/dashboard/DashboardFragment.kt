package com.example.giftsapp.ui.fragments.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentDashboardBinding
import com.example.giftsapp.navigation.extensions.navigate

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private lateinit var binding: FragmentDashboardBinding
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        binding.userTextView.text = "Welcome ${App.app!!.getFirebaseAuth().currentUser?.email}"
        signOut()
    }

    private fun signOut() {
        binding.signOutBtn.setOnClickListener {
            App.app!!.getFirebaseAuth().signOut()
            navigate(R.id.navigateToSignInFragment)
        }
    }
}
