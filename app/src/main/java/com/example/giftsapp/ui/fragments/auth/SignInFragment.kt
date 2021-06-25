package com.example.giftsapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentSigninBinding
import com.example.giftsapp.navigation.extensions.navigate
import com.example.giftsapp.services.FirebaseServices.signInUser

class SignInFragment : Fragment(R.layout.fragment_signin) {
    private lateinit var binding: FragmentSigninBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSigninBinding.bind(view)
        if (isUserSignedIn()) {
            navigate(R.id.dashboardFragment)
        }
        navigateToSignUp()
        signIn()
    }

    private fun isUserSignedIn() = App.app!!.getFirebaseAuth().currentUser?.email != null

    private fun navigateToSignUp() {
        binding.navigateToSignUpBtn.setOnClickListener {
            navigate(R.id.navigateToSignUpFragment)
        }
    }

    private fun signIn() {
        binding.apply {
            signInBtn.setOnClickListener {
                val email = signInInputEmailView.text.toString().trim()
                val password = signInInputPasswordView.text.toString().trim()
                signInUser(requireActivity(), email, password) {
                    if (it) {
                        navigate(R.id.navigateToDashboardFragment)
                    }
                }
            }
        }
    }
}