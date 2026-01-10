package com.example.qkart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.AllItemAdapter
import com.example.qkart.databinding.ActivityAllItemBinding
import com.example.qkart.model.AdminFoodModel
import com.google.firebase.database.*

class AllItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllItemBinding
    private val foodList = mutableListOf<AdminFoodModel>()
    private lateinit var adapter: AllItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AllItemAdapter(foodList)
        binding.AdminMenuRecyclerView.layoutManager =
            LinearLayoutManager(this)
        binding.AdminMenuRecyclerView.adapter = adapter

        loadFoodItems()

        binding.AdminBackButtonallitem.setOnClickListener {
            finish()
        }
    }

    private fun loadFoodItems() {
        FirebaseDatabase.getInstance()
            .reference
            .child("foods")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    foodList.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue(AdminFoodModel::class.java)
                        if (item != null) {
                            foodList.add(item)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}
