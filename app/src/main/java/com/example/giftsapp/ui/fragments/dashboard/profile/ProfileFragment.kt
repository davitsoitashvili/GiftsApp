package com.example.giftsapp.ui.fragments.dashboard.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentProfileBinding
import com.example.giftsapp.services.FirebaseServices.changeUserPassword
import com.example.giftsapp.tools.validators.InputValidationException
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmptyInput
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordConfirmationMatch
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordContainsChar
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordContainsDigit
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordLength

class ProfileFragment(val isSignedOut: (Boolean) -> Unit) : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        displayUserEmail()
        signOut()
        changePassword()
    }

    @SuppressLint("SetTextI18n")
    private fun displayUserEmail() {
        binding.userEmailAddressView.text =
            "Email Address ${App.app!!.getFirebaseAuth().currentUser?.email}"
    }

    private fun changePassword() {
        with(binding) {
            changePasswordBtn.setOnClickListener {
                val password = changePasswordInputView.text.toString()
                val confirmPassword = changePasswordConfirmationInputView.text.toString()
                try {
                    validateOnEmptyInput(changePasswordInputView)
                    validateOnEmptyInput(changePasswordConfirmationInputView)
                    validateOnPasswordContainsChar(changePasswordInputView)
                    validateOnPasswordContainsDigit(changePasswordInputView)
                    validateOnPasswordLength(changePasswordInputView)
                    validateOnPasswordContainsChar(changePasswordConfirmationInputView)
                    validateOnPasswordContainsDigit(changePasswordConfirmationInputView)
                    validateOnPasswordLength(changePasswordConfirmationInputView)
                    validateOnPasswordConfirmationMatch(password,confirmPassword)
                } catch (exception: InputValidationException) {
                    return@setOnClickListener
                }
                changeUserPassword(password)
            }
        }
    }

    private fun signOut() {
        binding.signOutBtn.setOnClickListener {
            App.app!!.getFirebaseAuth().signOut()
            isSignedOut(true)
        }
    }
}