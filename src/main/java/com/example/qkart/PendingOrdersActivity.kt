package com.example.qkart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.PendingOrderAdapter
import com.example.qkart.databinding.ActivityPendingOrdersBinding
import com.example.qkart.model.OrderModel
import com.google.firebase.database.*

class PendingOrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendingOrdersBinding
    private lateinit var adapter: PendingOrderAdapter
    private val orderList = mutableListOf<OrderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PendingOrderAdapter(orderList)
        binding.pendingorderRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.pendingorderRecyclerView.adapter = adapter

        binding.pendingordersbackbutton.setOnClickListener {
            finish()
        }

        fetchPendingOrders()
    }

    private fun fetchPendingOrders() {
        FirebaseDatabase.getInstance()
            .getReference("pendingOrders")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    orderList.clear()
                    for (orderSnap in snapshot.children) {
                        val order = orderSnap.getValue(OrderModel::class.java)
                        if (order != null) {
                            orderList.add(order)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}
