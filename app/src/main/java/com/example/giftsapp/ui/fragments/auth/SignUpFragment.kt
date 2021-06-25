package com.example.giftsapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentSignupBinding
import com.example.giftsapp.navigation.extensions.navigate
import com.example.giftsapp.services.FirebaseServices.createNewUser
import com.example.giftsapp.ui.fragments.dashboard.DashboardFragment

class SignUpFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(requireView())
        initClickListeners()
        signUp()
    }

    private fun initClickListeners() {
        binding.navigateToSignInBtn.setOnClickListener {
            navigate(R.id.navigateToSignInFragment)
        }
    }

    private fun signUp() {
        binding.apply {
            signUpBtn.setOnClickListener {
                val emailAddress = signUpInputEmailView.text.toString().trim()
                val password = signUpInputPasswordView.text.toString().trim()
                createNewUser(requireActivity(), emailAddress, password) {
                    if (it) {
                        navigate(R.id.navigateToSignInFragment)
                    }
                }
            }
        }
    }
}