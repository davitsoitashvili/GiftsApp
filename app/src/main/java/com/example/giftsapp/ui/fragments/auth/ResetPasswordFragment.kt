package com.example.giftsapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentResetPasswordBinding
import com.example.giftsapp.navigation.extensions.navigate
import com.example.giftsapp.services.FirebaseServices.resetUserPassword

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    private lateinit var binding: FragmentResetPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResetPasswordBinding.bind(view)
        resetPassword()
    }

    private fun resetPassword() {
        with(binding) {
            resetPasswordBtn.setOnClickListener {
                val emailAddress = inputEmailAddressView.text.toString().trim()
                resetUserPassword(emailAddress) {
                    if (it) {
                        navigate(R.id.navigateToSignInFragmentAfterReset)
                    }
                }
            }
        }
    }
}