package com.example.qkart.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.CongratsBottomSheet
import com.example.qkart.PayOutActivity
import com.example.qkart.R
import com.example.qkart.adaptar.CartAddaptar
import com.example.qkart.databinding.FragmentCartBinding
import java.util.ArrayList


class CartFragment : Fragment() {
private lateinit var binding : FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        val cartfoodname = listOf("Veg Burger", "Maggi", "Potato Patties", "Sandwich")
        val cartprice = listOf("Rs.40", "Rs.25", "Rs.20", "Rs.35")
        val cartimage = listOf(
            R.drawable.vegburger,
            R.drawable.maggi,
            R.drawable.potatopatties,
            R.drawable.sandwitch
        )
        val adapter = CartAddaptar(ArrayList(cartfoodname), ArrayList(cartprice), ArrayList(cartimage))
        binding.cartrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.cartrecyclerview.adapter = adapter

        binding.proceedbutton.setOnClickListener {
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }



        return binding.root
    }


}