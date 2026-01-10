package com.example.qkart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.databinding.ActivityAddItemBinding
import com.example.qkart.model.AdminAddItemModel
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding

    // 🔹 TEMP image list (local drawables)
    private val imageList = listOf("burger", "pizza", "momos")
    private var selectedImage = "burger" // default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔹 TEMP image switch (tap to change)
        binding.Selectimage.setOnClickListener {
            selectedImage = imageList.random()
            val imageId = resources.getIdentifier(
                selectedImage,
                "drawable",
                packageName
            )
            binding.selectedimage.setImageResource(imageId)
        }

        binding.button4.setOnClickListener {
            saveItem()
        }

        binding.AdminBackButtonadditem.setOnClickListener {
            finish()
        }
    }

    private fun saveItem() {
        val name = binding.textView47.text.toString().trim()
        val price = binding.textView48.text.toString().trim()

        if (name.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val foodId = UUID.randomUUID().toString()

        val food = AdminAddItemModel(
            foodId = foodId,
            foodName = name,
            foodPrice = price,
            imageName = selectedImage
        )

        FirebaseDatabase.getInstance()
            .reference
            .child("foods")
            .child(foodId)
            .setValue(food)
            .addOnSuccessListener {
                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
    }

    /*
    🔴 FUTURE FIREBASE STORAGE CODE (DO NOT DELETE)

    private fun uploadImageToFirebase(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance()
            .reference.child("food_images/${UUID.randomUUID()}.jpg")

        storageRef.putFile(uri)
            .continueWithTask { storageRef.downloadUrl }
            .addOnSuccessListener { url ->
                // save url.toString() in database
            }
    }
    */
}
