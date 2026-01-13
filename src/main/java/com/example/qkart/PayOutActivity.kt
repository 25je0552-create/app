package com.example.qkart

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.qkart.databinding.ActivityPayOutBinding
import com.example.qkart.model.OrderItem
import com.example.qkart.model.OrderModel
import com.example.qkart.utils.CartManager
import com.google.firebase.database.FirebaseDatabase

class PayOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPayOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔙 Back Button
        binding.backBtn.setOnClickListener {
            finish()
        }

        // 💰 Show total amount from cart
        showTotalAmount()

        // 🟢 Place Order Button
        binding.placeOrderBtn.setOnClickListener {
            if (validateInputs()) {
                placeOrder()
            }
        }
    }

    // ================= TOTAL AMOUNT =================
    private fun showTotalAmount() {
        val total = CartManager.getItems().sumOf {
            (it.foodPrice.toIntOrNull() ?: 0) * it.quantity
        }
        binding.amountEt.setText("Rs. $total")
    }

    // ================= INPUT VALIDATION =================
    private fun validateInputs(): Boolean {

        val name = binding.nameEt.text.toString().trim()
        val address = binding.addressEt.text.toString().trim()
        val phone = binding.phoneEt.text.toString().trim()

        if (name.isEmpty()) {
            showToast("Please enter your name")
            return false
        }

        if (address.isEmpty()) {
            showToast("Please enter your address")
            return false
        }

        if (phone.isEmpty() || phone.length < 10) {
            showToast("Please enter a valid phone number")
            return false
        }

        if (CartManager.getItems().isEmpty()) {
            showToast("Your cart is empty")
            return false
        }

        return true
    }

    // ================= PLACE ORDER =================
    private fun placeOrder() {

        val databaseRef = FirebaseDatabase.getInstance()
            .getReference("pendingOrders")

        val orderId = databaseRef.push().key ?: return

        val orderItems = CartManager.getItems().map {
            OrderItem(
                foodName = it.foodName,
                foodPrice = it.foodPrice,
                quantity = it.quantity
            )
        }

        val totalAmount = CartManager.getItems().sumOf {
            (it.foodPrice.toIntOrNull() ?: 0) * it.quantity
        }

        val order = OrderModel(
            orderId = orderId,
            userName = binding.nameEt.text.toString(),
            address = binding.addressEt.text.toString(),
            phone = binding.phoneEt.text.toString(),
            totalAmount = totalAmount,
            status = "Pending",
            items = orderItems
        )

        databaseRef.child(orderId).setValue(order)
            .addOnSuccessListener {
                Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
                CartManager.clearCart()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to place order", Toast.LENGTH_SHORT).show()
            }
    }

    // ================= TOAST =================
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
