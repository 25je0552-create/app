package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.PendingOrderItemBinding
import com.example.qkart.model.OrderModel
import com.google.firebase.database.FirebaseDatabase

class PendingOrderAdapter(
    private val orders: MutableList<OrderModel>
) : RecyclerView.Adapter<PendingOrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(
        val binding: PendingOrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]

        holder.binding.customername.text = order.userName
        holder.binding.quantityinput.text = order.items.toString()

        // 🔁 BUTTON VISIBILITY LOGIC
        if (order.status == "Accepted") {
            holder.binding.button6.visibility = View.GONE
            holder.binding.dispatchButton.visibility = View.VISIBLE
        } else {
            holder.binding.button6.visibility = View.VISIBLE
            holder.binding.dispatchButton.visibility = View.GONE
        }

        // ✅ ACCEPT BUTTON
        holder.binding.button6.setOnClickListener {
            val ref = FirebaseDatabase.getInstance()
                .getReference("pendingOrders")
                .child(order.orderId)

            ref.child("status").setValue("Accepted")
        }

        // 🚚 DISPATCH BUTTON
        holder.binding.dispatchButton.setOnClickListener {
            val ref = FirebaseDatabase.getInstance()
                .getReference("pendingOrders")
                .child(order.orderId)

            // remove order from pending list
            ref.removeValue()
        }
    }

    override fun getItemCount(): Int = orders.size
}
