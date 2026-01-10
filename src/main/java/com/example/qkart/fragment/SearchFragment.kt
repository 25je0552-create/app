package com.example.qkart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.MenuAdapter
import com.example.qkart.databinding.FragmentSearchBinding
import com.example.qkart.model.FoodModel
import com.google.firebase.database.*

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter

    // 🔹 Full list from Firebase
    private val fullMenuList = mutableListOf<FoodModel>()

    // 🔹 Filtered list (shown in RecyclerView)
    private val filteredMenuList = mutableListOf<FoodModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // Adapter uses FILTERED list
        adapter = MenuAdapter(filteredMenuList)

        binding.menurecyclerview.layoutManager =
            LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter

        setupSearchView()
        fetchMenuFromFirebase()

        return binding.root
    }

    // 🔥 FETCH DATA FROM FIREBASE
    private fun fetchMenuFromFirebase() {
        val database = FirebaseDatabase.getInstance().reference

        database.child("foods")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    fullMenuList.clear()
                    filteredMenuList.clear()

                    for (itemSnapshot in snapshot.children) {
                        val food = itemSnapshot.getValue(FoodModel::class.java)
                        if (food != null) {
                            fullMenuList.add(food)
                        }
                    }

                    // Show all initially
                    filteredMenuList.addAll(fullMenuList)
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    // 🔍 SEARCH VIEW
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterMenu(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterMenu(newText)
                    return true
                }
            }
        )
    }

    // 🔎 FILTER LOGIC
    private fun filterMenu(query: String?) {
        val searchText = query?.trim()?.lowercase() ?: ""

        filteredMenuList.clear()

        if (searchText.isEmpty()) {
            filteredMenuList.addAll(fullMenuList)
        } else {
            for (item in fullMenuList) {
                if (item.foodName.lowercase().contains(searchText)) {
                    filteredMenuList.add(item)
                }
            }
        }

        adapter.notifyDataSetChanged()
    }
}
