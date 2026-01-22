package com.example.qkart.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.BuyAgainAdapter
import com.example.qkart.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // ✅ FIRST initialize binding
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        // ✅ THEN use binding
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        val foodNames = arrayListOf("Veg Burger", "Maggi", "Sandwich")
        val prices = arrayListOf("Rs.40", "Rs.25", "Rs.35")
        val images = arrayListOf(
            com.example.qkart.R.drawable.vegburger,
            com.example.qkart.R.drawable.maggi,
            com.example.qkart.R.drawable.sandwitch
        )

        val adapter = BuyAgainAdapter(foodNames, prices, images)

        binding.buyagainRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.buyagainRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
