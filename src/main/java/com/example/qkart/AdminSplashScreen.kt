package com.example.qkart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class AdminSplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_splash_screen)

        // Splash delay (2 seconds)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 👈 REQUIRED delay in milliseconds
    }
}
