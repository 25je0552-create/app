package com.example.qkart.model

data class AdminFoodModel(
    val imageRes: Int,
    val name: String,
    val price: String,
    var quantity: Int = 0
)
