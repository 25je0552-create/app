package com.example.qkart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.qkart.adaptar.MenuAdapter
import com.example.qkart.databinding.FragmentMenubottomsheetBinding

class MenubottomsheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMenubottomsheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenubottomsheetBinding.inflate(inflater, container, false)
        binding.buttonback.setOnClickListener {
            dismiss()
        }
        val menuFoodName = listOf("Veg Burger", "Maggi", "Potato Patties", "Sandwich")
        val menuPrice = listOf("Rs.40", "Rs.25", "Rs.20", "Rs.35")
        val menuImage = listOf(
            R.drawable.vegburger,
            R.drawable.maggi,
            R.drawable.potatopatties,
            R.drawable.sandwitch
        )

        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuPrice),
            ArrayList(menuImage)
        )

        binding.menuRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL , false)



        binding.menuRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
