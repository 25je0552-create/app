package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.PendingOrderItemBinding
import com.example.qkart.model.OrderModel

class PendingOrderAdapter(
    private val orders: MutableList<OrderModel>
) : RecyclerView.Adapter<PendingOrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: PendingOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root)

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

        // ✅ Customer Name
        holder.binding.customername.text = order.userName

        // ✅ Total Quantity (FIXED)
        val totalQty = order.items.sumOf { it.quantity }
        holder.binding.quantityinput.text = totalQty.toString()

        // ✅ Accept Button
        holder.binding.button6.setOnClickListener {
            holder.binding.button6.visibility = View.GONE
            holder.binding.dispatchButton.visibility = View.VISIBLE
        }

        // ✅ Dispatch Button
        holder.binding.dispatchButton.setOnClickListener {
            orders.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = orders.size
}
