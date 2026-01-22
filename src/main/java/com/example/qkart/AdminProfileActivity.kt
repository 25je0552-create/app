package com.example.qkart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.databinding.ActivityAdminProfileBinding
import com.example.qkart.model.AdminProfileModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
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

        // Save profile
        binding.AdminSaveInfoButton.setOnClickListener {
            saveAdminProfile()
        }
    }

    private fun saveAdminProfile() {

        val name = binding.nameAdmininput.text.toString().trim()
        val address = binding.addressAdmininput.text.toString().trim()
        val email = binding.emailAdmininput.text.toString().trim()
        val phone = binding.phoneAdmininput.text.toString().trim()
        val password = binding.passwordAdmininput.text.toString().trim()


        if (name.isEmpty() || address.isEmpty() || email.isEmpty()
            || phone.isEmpty() || password.isEmpty()
        ) {
            Toast.makeText(this, "Fill all credentials", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "Admin not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val adminProfile = AdminProfileModel(
            name = name,
            address = address,
            email = email,
            phone = phone,
            password = password
        )

        database.child("Admins")
            .child(uid)
            .child("Profile")
            .setValue(adminProfile)
            .addOnSuccessListener {
                Toast.makeText(this, "Admin profile saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show()
            }
    }
}
