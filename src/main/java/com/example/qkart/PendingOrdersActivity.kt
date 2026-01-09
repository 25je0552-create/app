package com.example.qkart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.databinding.ActivityPendingOrdersBinding

class PendingOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendingOrdersBinding
    private lateinit var adapter: PendingOrderAdapter
    private val orderList = mutableListOf<PendingOrder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPendingOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pendingordersbackbutton.setOnClickListener {
            finish()
        }

        loadDummyOrders()

        adapter = PendingOrderAdapter(orderList)
        binding.pendingorderRecyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.pendingorderRecyclerView.adapter = adapter
    }

    private fun loadDummyOrders() {
        orderList.add(PendingOrder("Akshat Gupta", 2))
        orderList.add(PendingOrder("Anjali Verma", 5))
        orderList.add(PendingOrder("Amit Kumar", 1))
    }
}
