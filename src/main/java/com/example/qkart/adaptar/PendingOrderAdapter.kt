package com.example.qkart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.PendingOrderItemBinding

class PendingOrderAdapter(
    private val orderList: MutableList<PendingOrder>
) : RecyclerView.Adapter<PendingOrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(
        private val binding: PendingOrderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: PendingOrder, position: Int) {
            binding.customername.text = order.customerName
            binding.quantityinput.text = order.quantity.toString()

            if (order.isAccepted) {
                binding.button6.text = "Dispatch"
            } else {
                binding.button6.text = "Accept"
            }

            binding.button6.setOnClickListener {
                if (!order.isAccepted) {
                    // Accept order
                    order.isAccepted = true
                    notifyItemChanged(position)
                } else {

                    orderList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, orderList.size)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = PendingOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderList[position], position)
    }

    override fun getItemCount(): Int = orderList.size
}
