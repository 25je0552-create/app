package com.example.qkart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.AllItemAdapter
import com.example.qkart.databinding.ActivityAllItemBinding
import com.example.qkart.model.AdminFoodModel

class AllItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodList = mutableListOf(
            AdminFoodModel(R.drawable.vegburger, "Veg Burger", "Rs.40", 10),
            AdminFoodModel(R.drawable.potatopatties, "PotatoPaties", "Rs.20", 10),
            AdminFoodModel(R.drawable.sandwitch, "Sandwitch", "Rs.35", 10)
        )

        binding.AdminMenuRecyclerView.layoutManager =
            LinearLayoutManager(this)

        binding.AdminMenuRecyclerView.adapter =
            AllItemAdapter(foodList)

        binding.AdminBackButtonallitem.setOnClickListener {
            finish()
        }
    }
}
