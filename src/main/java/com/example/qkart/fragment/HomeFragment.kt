package com.example.qkart.fragment

import android.os.Bundle
import android.os.TestLooperManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.MenubottomsheetFragment
import com.example.qkart.R
import com.example.qkart.adaptar.PopularAddaptar
import com.example.qkart.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmenubutton.setOnClickListener {
            val bottomSheetDialog = MenubottomsheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Menubottonsheet")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* ================= IMAGE SLIDER (COMMENTED FOR LATER USE) =================

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        ========================================================================== */

        // ---------- POPULAR ITEMS DATA ----------
        val foodName = listOf("Veg Burger", "Maggi", "Potato Patties", "Sandwich")
        val price = listOf("Rs.40", "Rs.25", "Rs.20", "Rs.35")
        val popularFoodImages = listOf(
            R.drawable.vegburger,
            R.drawable.maggi,
            R.drawable.potatopatties,
            R.drawable.sandwitch
        )

        // ---------- ADAPTER ----------
        val adapter = PopularAddaptar(
            Items = foodName,
            price = price,
            image = popularFoodImages
        )

        // ---------- RECYCLERVIEW ----------
        binding.popularrecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.popularrecyclerview.setHasFixedSize(true)
        binding.popularrecyclerview.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




