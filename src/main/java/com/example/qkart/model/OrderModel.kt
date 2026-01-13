package com.example.qkart.model

data class OrderModel(
    val orderId: String = "",
    val userName: String = "",
    val address: String = "",
    val phone: String = "",
    val totalAmount: Int = 0,
    val status: String = "Pending",
    val items: List<OrderItem> = emptyList()
)
