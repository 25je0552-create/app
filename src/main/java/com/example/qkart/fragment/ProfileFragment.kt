package com.example.qkart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.qkart.databinding.FragmentProfileBinding
import com.example.qkart.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            saveProfile()
        }

        return binding.root
    }

    private fun saveProfile() {

        val name = binding.nameinput.text.toString().trim()
        val roomNo = binding.addressinput.text.toString().trim()
        val email = binding.emailinput.text.toString().trim()
        val phone = binding.editTextText2.text.toString().trim()

        if (name.isEmpty() || roomNo.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val profile = UserProfile(
            name = name,
            roomNo = roomNo,
            email = email,
            phone = phone
        )

        database.child("Users")
            .child(uid)
            .child("Profile")
            .setValue(profile)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to save profile", Toast.LENGTH_SHORT).show()
            }
    }
}
