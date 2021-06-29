package com.example.giftsapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentResetPasswordBinding
import com.example.giftsapp.services.FirebaseServices.resetUserPassword
import com.example.giftsapp.tools.extensions.navigate
import com.example.giftsapp.tools.validators.InputValidationException
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmailFormat
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmptyInput

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {
    private lateinit var binding: FragmentResetPasswordBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResetPasswordBinding.bind(view)
        resetPassword()
        navigateToSignIn()
    }

    private fun resetPassword() {
        with(binding) {
            resetPasswordBtn.setOnClickListener {
                try {
                    validateOnEmptyInput(inputEmailAddressView)
                    validateOnEmailFormat(inputEmailAddressView)
                }catch (exception : InputValidationException) {
                    return@setOnClickListener
                }
                val emailAddress = inputEmailAddressView.text.toString().trim()
                resetUserPassword(emailAddress) {
                    if (it) {
                        navigate(R.id.navigateToSignInFragmentFromReset)
                    }
                }
            }
        }
    }

    private fun navigateToSignIn() {
        binding.navigateToSignInBtn.setOnClickListener {
            navigate(R.id.navigateToSignInFragmentFromReset)
        }
    }
}