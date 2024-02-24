package com.sks.todoappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sks.todoappwithfirebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        loginBinding.loginBtn.setOnClickListener {
            loginUser()
        }

        loginBinding.createAccountTextViewBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = loginBinding.emailEditText.text.toString()
        val password = loginBinding.passwordEditText.text.toString()

        val isValidated = validateData(email, password)
        if (!isValidated) {
            return
        }

        loginAccountInFirebase(email, password)
    }

    private fun loginAccountInFirebase(email: String, password: String) {
        changeInProgress(true)

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            changeInProgress(false)
            if (task.isSuccessful) {
                if (auth.currentUser?.isEmailVerified == true) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Utility.showToast(
                        applicationContext, "Email not verified. Please verify your email."
                    )
                }
            } else {
                Utility.showToast(
                    applicationContext,
                    task.exception?.localizedMessage ?: "Something went wrong."
                )
            }
        }
    }

    private fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            loginBinding.progressBar.visibility = View.VISIBLE
            loginBinding.loginBtn.visibility = View.GONE
        } else {
            loginBinding.progressBar.visibility = View.GONE
            loginBinding.loginBtn.visibility = View.VISIBLE
        }
    }

    private fun validateData(email: String, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginBinding.emailEditText.error = "Please enter a valid email address"
            return false
        }
        if (password.length < 6) {
            loginBinding.passwordEditText.error = "Password length should be minimum 6 characters"
            return false
        }
        return true
    }
}