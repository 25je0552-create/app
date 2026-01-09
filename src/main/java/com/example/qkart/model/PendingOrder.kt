package com.example.qkart

data class PendingOrder(
    val customerName: String,
    val quantity: Int,
    var isAccepted: Boolean = false
)
