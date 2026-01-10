package com.example.qkart.utils

import com.example.qkart.model.CartItem

object CartManager {

    private val cartItems = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        val existingItem = cartItems.find { it.foodName == item.foodName }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(item)
        }
    }

    fun getItems(): MutableList<CartItem> = cartItems

    fun removeItem(position: Int) {
        if (position in cartItems.indices) {
            cartItems.removeAt(position)
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
