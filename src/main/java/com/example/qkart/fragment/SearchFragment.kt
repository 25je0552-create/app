package com.example.qkart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.R
import com.example.qkart.adaptar.MenuAdapter
import com.example.qkart.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter

    // ORIGINAL DATA
    private val originalMenuFoodname = listOf(
        "Veg Burger",
        "Maggi",
        "Potato Patties",
        "Sandwich"
    )

    private val originalMenuPrice = listOf(
        "Rs.40",
        "Rs.25",
        "Rs.20",
        "Rs.35"
    )

    private val originalMenuImage = listOf(
        R.drawable.vegburger,
        R.drawable.maggi,
        R.drawable.potatopatties,
        R.drawable.sandwitch
    )

    // FILTERED DATA
    private val filteredMenuFoodname = mutableListOf<String>()
    private val filteredMenuPrice = mutableListOf<String>()
    private val filteredMenuImage = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)

        adapter = MenuAdapter(
            filteredMenuFoodname,
            filteredMenuPrice,
            filteredMenuImage
        )

        binding.menurecyclerview.layoutManager =
            LinearLayoutManager(requireContext())
        binding.menurecyclerview.adapter = adapter

        setupSearchView()
        showAllMenu()

        return binding.root
    }

    // SHOW ALL ITEMS
    private fun showAllMenu() {
        filteredMenuFoodname.clear()
        filteredMenuPrice.clear()
        filteredMenuImage.clear()

        filteredMenuFoodname.addAll(originalMenuFoodname)
        filteredMenuPrice.addAll(originalMenuPrice)
        filteredMenuImage.addAll(originalMenuImage)

        adapter.notifyDataSetChanged()
    }

    // SEARCH VIEW SETUP
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    filterMenuItem(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterMenuItem(newText)
                    return true
                }
            }
        )
    }

    // FILTER LOGIC (NULL SAFE)
    private fun filterMenuItem(query: String?) {
        val searchText = query?.trim()?.lowercase() ?: ""

        filteredMenuFoodname.clear()
        filteredMenuPrice.clear()
        filteredMenuImage.clear()

        if (searchText.isEmpty()) {
            showAllMenu()
            return
        }

        originalMenuFoodname.forEachIndexed { index, foodName ->
            if (foodName.lowercase().contains(searchText)) {
                filteredMenuFoodname.add(foodName)
                filteredMenuPrice.add(originalMenuPrice[index])
                filteredMenuImage.add(originalMenuImage[index])
            }
        }

        adapter.notifyDataSetChanged()
    }
}
