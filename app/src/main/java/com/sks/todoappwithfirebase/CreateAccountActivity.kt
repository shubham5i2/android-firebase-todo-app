package com.sks.todoappwithfirebase

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sks.todoappwithfirebase.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var createAccountBinding: ActivityCreateAccountBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
        if (!isValidated) {
            return
        }

        createAccountInFirebase(email, password);

    }

    private fun createAccountInFirebase(email: String, password: String) {
        changeInProgress(true)

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            changeInProgress(false)
            if (task.isSuccessful) {
                Utility.showToast(
                    applicationContext,
                    "Your account has been created. Please check email to verify."
                )

                //send a verification email to the current user and logout from the application
                auth.currentUser?.sendEmailVerification()
                auth.signOut()
                finish()
            } else {
                Utility.showToast(
                    applicationContext, task.exception?.localizedMessage ?: "Something went wrong."
                )
            }
        }
    }

    private fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            createAccountBinding.progressBar.visibility = View.VISIBLE
            createAccountBinding.createAccountBtn.visibility = View.GONE
        } else {
            createAccountBinding.progressBar.visibility = View.GONE
            createAccountBinding.createAccountBtn.visibility = View.VISIBLE
        }
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