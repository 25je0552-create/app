package com.example.qkart

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_splash)


        window.decorView.postDelayed({
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }, 3000)
    }
}
