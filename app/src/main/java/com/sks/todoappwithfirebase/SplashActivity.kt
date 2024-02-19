package com.sks.todoappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                val intent = Intent(this@SplashActivity, CreateAccountActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)
    }
}