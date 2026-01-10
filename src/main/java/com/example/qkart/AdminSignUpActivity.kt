package com.example.qkart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.databinding.ActivityAdminSignUpBinding
import com.example.qkart.model.AdminModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class AdminSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // 🔹 Google Sign-In config (ACCOUNT CHOOSER ENABLED)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 🔹 Email/Password Admin Signup
        binding.button3.setOnClickListener {
            val owner = binding.editTextText.text.toString().trim()
            val restaurant = binding.editTextText3.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (owner.isEmpty() || restaurant.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    saveAdminToDatabase(owner, restaurant, email)
                    startActivity(Intent(this, AdminMainActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
        }

        // 🔹 Google Signup Button
        binding.button2.setOnClickListener {
            googleSignInClient.signOut() // forces account chooser
            val intent = googleSignInClient.signInIntent
            googleLauncher.launch(intent)
        }

        // 🔹 Go to Admin Login
        binding.AlreadyHaveAdmin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
            finish()
        }
    }

    // 🔹 Google Sign-In Result
    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val email = account.email ?: return@registerForActivityResult

                    // 🔴 CHECK DUPLICATE ACCOUNT
                    checkIfAdminExists(email) { exists ->
                        if (exists) {
                            Toast.makeText(
                                this,
                                "This account is already registered. Please log in.",
                                Toast.LENGTH_LONG
                            ).show()

                            auth.signOut()
                            googleSignInClient.signOut()
                        } else {
                            firebaseAuthWithGoogle(account.idToken!!)
                        }
                    }

                } catch (e: Exception) {
                    Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    // 🔹 Firebase Auth with Google
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                val user = auth.currentUser ?: return@addOnSuccessListener

                saveAdminToDatabase(
                    owner = binding.editTextText.text.toString(),
                    restaurant = binding.editTextText3.text.toString(),
                    email = user.email!!
                )

                startActivity(Intent(this, AdminMainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
    }

    // 🔹 Save Admin Data
    private fun saveAdminToDatabase(owner: String, restaurant: String, email: String) {
        val uid = auth.currentUser?.uid ?: return
        val safeEmail = email.replace(".", "_")

        val admin = AdminModel(
            ownerName = owner,
            restaurantName = restaurant,
            email = email
        )

        val db = FirebaseDatabase.getInstance().reference
        db.child("admins").child(uid).setValue(admin)
        db.child("adminsByEmail").child(safeEmail).setValue(uid)
    }

    // 🔹 Check Duplicate Google Account
    private fun checkIfAdminExists(email: String, callback: (Boolean) -> Unit) {
        val safeEmail = email.replace(".", "_")

        FirebaseDatabase.getInstance()
            .reference
            .child("adminsByEmail")
            .child(safeEmail)
            .get()
            .addOnSuccessListener { snapshot ->
                callback(snapshot.exists())
            }
    }
}
