package com.example.qkart.model

data class CartItem(
    val foodName: String,
    val foodPrice: String,
    val imageRes: Int,
    var quantity: Int = 1
)
