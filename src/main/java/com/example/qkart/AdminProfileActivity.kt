package com.example.qkart

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.qkart.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {

    private val binding : ActivityAdminProfileBinding by lazy{
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        binding.imageButton41.setOnClickListener {
            finish()
        }
        binding.nameAdmininput.isEnabled = false
        binding.emailAdmininput.isEnabled = false
        binding.addressAdmininput.isEnabled = false
        binding.phoneAdmininput.isEnabled = false
        binding.passwordAdmininput.isEnabled = false

        var isEnable = false
        binding.AdminEditButton.setOnClickListener {
            isEnable = !isEnable
            binding.nameAdmininput.isEnabled = isEnable
            binding.emailAdmininput.isEnabled = isEnable
            binding.addressAdmininput.isEnabled = isEnable
            binding.phoneAdmininput.isEnabled = isEnable
            binding.passwordAdmininput.isEnabled = isEnable
            if (isEnable){
                binding.nameAdmininput.requestFocus()
            }

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}