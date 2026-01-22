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
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 🔹 View Menu Button → Bottom Sheet
        binding.viewmenubutton.setOnClickListener {
            MenubottomsheetFragment()
                .show(parentFragmentManager, "MenuBottomSheet")
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 RecyclerView setup
        menuAdapter = MenuAdapter(foodList)

        binding.popularrecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.popularrecyclerview.adapter = menuAdapter

        // 🔹 Load food from Firebase
        loadFoodFromFirebase()
    }

    private fun loadFoodFromFirebase() {

        FirebaseDatabase.getInstance()
            .getReference("foods")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    foodList.clear()

                    for (data in snapshot.children) {
                        val food = data.getValue(FoodModel::class.java)
                        if (food != null) {
                            foodList.add(food)
                        }
                    }

                    menuAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // optional: log error
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
