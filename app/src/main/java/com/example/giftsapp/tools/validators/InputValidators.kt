package com.example.giftsapp.tools.validators

import android.widget.EditText
import com.example.giftsapp.tools.showToastMessage

object InputValidators {

    fun validateOnEmptyInput(inputView: EditText) {
        if (inputView.text.isEmpty()) {
            inputView.error = "Required, please fill this field!"
            throw InputValidationException()
        }
    }

    fun validateOnEmailFormat(emailInputView: EditText) {
        if (!emailInputView.text.toString().trim().contains("@")) {
            emailInputView.error = "email format is not valid!"
            throw InputValidationException()
        }
    }

    fun validateOnPasswordConfirmationMatch(password: String, confirmPassword: String) {
        if (password.trim() != confirmPassword.trim()) {
            showToastMessage("Password don't match, try again")
            throw InputValidationException()
        }
    }

    fun validateOnPasswordLength(passwordEditText : EditText) {
        if (passwordEditText.text.toString().trim().length < 8) {
            passwordEditText.error = "Password length must be minimum 8 characters"
            throw InputValidationException()
        }
    }

    fun validateOnPasswordContainsChar(passwordEditText : EditText) {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        passwordEditText.text.toString().trim().forEach {
            if (alphabet.contains(it.lowercase())) {
                return
            }
        }
        passwordEditText.error = "Password should contains alphabet char!"
        throw InputValidationException()
    }

    fun validateOnPasswordContainsDigit(passwordEditText : EditText) {
        passwordEditText.text.toString().trim().forEach {
            if (it.isDigit()) {
                return
            }
        }
        passwordEditText.error = "Password should contains digit!"
        throw InputValidationException()
    }

    fun validateOnPostTitleLength(postTitleEditText : EditText) {
        if (postTitleEditText.text.toString().length < 5) {
            postTitleEditText.error = "Post title should contains minimum 5 char!"
            throw InputValidationException()
        }
    }

    fun validateOnPostDescriptionLength(postDescriptionEditText : EditText) {
        if (postDescriptionEditText.text.toString().length < 12) {
            postDescriptionEditText.error = "Post description should contains minimum 12 char!"
            throw InputValidationException()
        }
    }
}