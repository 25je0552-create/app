package com.example.qkart

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.databinding.ActivityAddItemBinding
import com.example.qkart.model.AdminAddItemModel
import com.example.qkart.utils.CloudinaryUploader
import com.example.qkart.utils.FileUtils
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.util.UUID

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private var imageUri: Uri? = null
    private var uploadedImageUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.AdminBackButtonadditem.setOnClickListener {
            finish()
        }


        binding.Selectimage.setOnClickListener {
            pickImage()
        }


        binding.button4.setOnClickListener {
            saveItem()
        }
    }


    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            binding.selectedimage.setImageURI(imageUri)
        }
    }


    private fun saveItem() {

        val name = binding.textView47.text.toString().trim()
        val price = binding.textView48.text.toString().trim()

        if (name.isEmpty() || price.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Fill all fields & select image", Toast.LENGTH_SHORT).show()
            return
        }

        val file = FileUtils.getFileFromUri(this, imageUri!!)


        CloudinaryUploader.uploadImage(
            imageFile = file,
            onSuccess = { imageUrl ->
                uploadedImageUrl = imageUrl
                saveToDatabase(name, price, imageUrl)
            },
            onError = {
                runOnUiThread {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }


    private fun saveToDatabase(name: String, price: String, imageUrl: String) {

        val foodId = UUID.randomUUID().toString()

        val food = AdminAddItemModel(
            foodId = foodId,
            foodName = name,
            foodPrice = price,
            imageUrl = imageUrl
        )

        FirebaseDatabase.getInstance()
            .reference
            .child("foods")
            .child(foodId)
            .setValue(food)
            .addOnSuccessListener {
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
    }
}
