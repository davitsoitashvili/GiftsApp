package com.example.giftsapp.Validators

import java.util.regex.Matcher
import java.util.regex.Pattern

object Validators {
    fun isEmailValid(email: String): Boolean {
        email.trim()
        val expression = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+\$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isValidPassword(password: String): Boolean {
        password.trim()
        val PASSWORD_PATTERN = "^(?=.*[0-10])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}