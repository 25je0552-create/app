package com.example.qkart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.databinding.ActivityAdminLoginBinding
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.database.*

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // 🔵 GOOGLE SIGN-IN CONFIG
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // 🔐 EMAIL LOGIN
        binding.adminloginaccountbutton.setOnClickListener {
            loginWithEmail()
        }

        // 🔵 GOOGLE LOGIN BUTTON
        binding.adminLogingooglebutton.setOnClickListener {
            googleSignInClient.signOut() // force chooser
            googleLauncher.launch(googleSignInClient.signInIntent)
        }

        // ➕ SIGNUP
        binding.admindonotbutton.setOnClickListener {
            startActivity(Intent(this, AdminSignUpActivity::class.java))
        }
    }

    // ---------------- EMAIL LOGIN ----------------
    private fun loginWithEmail() {
        val email = binding.ADMINEMAILADDRESS.text.toString().trim()
        val password = binding.AdminPASSWORDLOGIN.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { checkAdminAccess() }
            .addOnFailureListener {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
    }

    // ---------------- GOOGLE RESULT ----------------
    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: Exception) {
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener { checkAdminAccess() }
            .addOnFailureListener {
                Toast.makeText(this, "Google auth failed", Toast.LENGTH_SHORT).show()
            }
    }

    // ---------------- ADMIN CHECK ----------------
    private fun checkAdminAccess() {
        val uid = auth.currentUser?.uid ?: return

        database.child("admins").child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        startActivity(Intent(this@AdminLoginActivity, AdminMainActivity::class.java))
                        finish()
                    } else {
                        auth.signOut()
                        Toast.makeText(
                            this@AdminLoginActivity,
                            "This Google account is not registered as Admin",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@AdminLoginActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }
}
