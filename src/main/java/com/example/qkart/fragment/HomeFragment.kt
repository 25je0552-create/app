package com.example.qkart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.MenubottomsheetFragment
import com.example.qkart.adaptar.MenuAdapter
import com.example.qkart.databinding.FragmentHomeBinding
import com.example.qkart.model.FoodModel
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val foodList = mutableListOf<FoodModel>()
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewmenubutton.setOnClickListener {
            MenubottomsheetFragment()
                .show(parentFragmentManager, "MenuBottomSheet")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MenuAdapter(foodList)

        binding.popularrecyclerview.layoutManager =
            LinearLayoutManager(requireContext())

        binding.popularrecyclerview.adapter = adapter

        loadFoodFromFirebase()
    }

    private fun loadFoodFromFirebase() {
        FirebaseDatabase.getInstance()
            .reference
            .child("foods")
            .addValueEventListener(object : ValueEventListener {
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
