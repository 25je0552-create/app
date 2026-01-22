package com.example.qkart.model

data class CartItem(
    val foodId: String = "",
    val foodName: String = "",
    val foodPrice: String = "",
    val imageUrl: String = "",
    var quantity: Int = 1
)
