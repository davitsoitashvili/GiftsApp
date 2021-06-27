package com.example.giftsapp.ui.fragments.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentSignupBinding
import com.example.giftsapp.services.FirebaseServices.createNewUser
import com.example.giftsapp.services.FirebaseServices.sendEmailVerification
import com.example.giftsapp.tools.extensions.navigate
import com.example.giftsapp.tools.showToastMessage
import com.example.giftsapp.tools.validators.InputValidationException
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmailFormat
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmptyInput
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordConfirmationMatch
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordContainsChar
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordContainsDigit
import com.example.giftsapp.tools.validators.InputValidators.validateOnPasswordLength

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
                val confirmPassword = signUpConfirmPasswordView.text.toString().trim()
                try {
                    validateOnEmptyInput(signUpInputEmailView)
                    validateOnEmptyInput(signUpInputPasswordView)
                    validateOnEmptyInput(signUpConfirmPasswordView)
                    validateOnEmailFormat(signUpInputEmailView)
                    validateOnPasswordContainsChar(signUpInputPasswordView)
                    validateOnPasswordContainsDigit(signUpInputPasswordView)
                    validateOnPasswordLength(signUpInputPasswordView)
                    validateOnPasswordContainsChar(signUpConfirmPasswordView)
                    validateOnPasswordContainsDigit(signUpConfirmPasswordView)
                    validateOnPasswordLength(signUpConfirmPasswordView)
                    validateOnPasswordConfirmationMatch(password,confirmPassword)
                } catch (exception : InputValidationException) {
                    return@setOnClickListener
                }

                createNewUser(requireActivity(), emailAddress, password) {
                    if (it) {
                        sendEmailVerification {
                            if (it) {
                                navigate(R.id.navigateToSignInFragment)
                                showToastMessage("Email Verification Link is sent to your email, please check !")
                            }
                        }
                    }
                }
            }
        }
    }
}