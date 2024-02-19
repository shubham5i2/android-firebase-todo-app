package com.sks.todoappwithfirebase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sks.todoappwithfirebase.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var createAccountBinding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createAccountBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        val view = createAccountBinding.root
        setContentView(view)
    }
}