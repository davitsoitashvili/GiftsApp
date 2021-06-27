package com.example.giftsapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentSigninBinding
import com.example.giftsapp.services.FirebaseServices.signInUser
import com.example.giftsapp.tools.extensions.navigate
import com.example.giftsapp.tools.showToastMessage
import com.example.giftsapp.tools.validators.InputValidationException
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmailFormat
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmptyInput
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordContainsChar
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordContainsDigit
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordLength

class SignInFragment : Fragment(R.layout.fragment_signin) {
    private lateinit var binding: FragmentSigninBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSigninBinding.bind(view)
        if (isUserEmailVerified()) {
            navigate(R.id.dashboardFragment)
        }
        navigateToSignUp()
        signIn()
        forgotPassword()
    }

    private fun isUserEmailVerified(): Boolean {
        if (App.app!!.getFirebaseAuth().currentUser?.email != null) {
            if (App.app!!.getFirebaseAuth().currentUser!!.isEmailVerified) {
                return true
            }
            return false
        }
        return false
    }

    private fun navigateToSignUp() {
        binding.navigateToSignUpBtn.setOnClickListener {
            navigate(R.id.navigateToSignUpFragment)
        }
    }

    private fun signIn() {
        binding.apply {
            signInBtn.setOnClickListener {

                try {
                    validateOnEmptyInput(signInInputEmailView)
                    validateOnEmptyInput(signInInputPasswordView)
                    validateOnEmailFormat(signInInputEmailView)
                    validateOnPasswordContainsChar(signInInputPasswordView)
                    validateOnPasswordContainsDigit(signInInputPasswordView)
                    validateOnPasswordLength(signInInputPasswordView)
                }catch (exception : InputValidationException) {
                    return@setOnClickListener
                }

                val email = signInInputEmailView.text.toString().trim()
                val password = signInInputPasswordView.text.toString().trim()

                signInUser(requireActivity(), email, password) {
                    if (it && isUserEmailVerified()) {
                        navigate(R.id.navigateToDashboardFragment)
                    } else {
                        showToastMessage("Account is not verified!, please check your email to confirm your account!")
                    }
                }
            }
        }
    }

    private fun forgotPassword() {
        binding.forgotPasswordView.setOnClickListener {
            navigate(R.id.navigateToResetPasswordFragment)
        }
    }

}