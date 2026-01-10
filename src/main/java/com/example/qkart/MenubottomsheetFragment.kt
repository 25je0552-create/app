package com.example.qkart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.MenuAdapter
import com.example.qkart.databinding.FragmentMenubottomsheetBinding
import com.example.qkart.model.FoodModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*

class MenubottomsheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMenubottomsheetBinding? = null
    private val binding get() = _binding!!

    private val foodList = mutableListOf<FoodModel>()
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenubottomsheetBinding.inflate(inflater, container, false)

        binding.buttonback.setOnClickListener {
            dismiss()
        }

        adapter = MenuAdapter(foodList)

        binding.menuRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.menuRecyclerView.adapter = adapter

        loadMenuItems()

        return binding.root
    }

    private fun loadMenuItems() {
        FirebaseDatabase.getInstance()
            .reference
            .child("foods")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    foodList.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue(FoodModel::class.java)
                        if (item != null) {
                            foodList.add(item)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
