package com.sks.todoappwithfirebase

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.sks.todoappwithfirebase.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var createAccountBinding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAccountBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        val view = createAccountBinding.root
        setContentView(view)

        createAccountBinding.createAccountBtn.setOnClickListener {
            createAccount()
        }

        createAccountBinding.loginTextViewBtn.setOnClickListener {
            finish()
        }
    }

    private fun createAccount() {
        val fullName = createAccountBinding.fullnameEditText.text.toString()
        val email = createAccountBinding.emailEditText.text.toString()
        val password = createAccountBinding.passwordEditText.text.toString()
        val confirmPassword = createAccountBinding.confirmPasswordEditText.text.toString()

        val isValidated = validateData(fullName, email, password, confirmPassword)

    }

    private fun validateData(
        fullName: String, email: String, password: String, confirmPassword: String
    ): Boolean {
        if (fullName.trim().equals("")) {
            createAccountBinding.fullnameEditText.error = "Full name is required"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            createAccountBinding.emailEditText.error = "Please enter a valid email address"
            return false
        }
        if (password.length < 6) {
            createAccountBinding.passwordEditText.error =
                "Password length should be minimum 6 characters"
            return false
        }
        if (!password.equals(confirmPassword)) {
            createAccountBinding.confirmPasswordEditText.error = "Passwords does not match"
            return false
        }
        return true
    }
}