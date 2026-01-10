package com.example.qkart.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.PayOutActivity
import com.example.qkart.adaptar.CartAddaptar
import com.example.qkart.databinding.FragmentCartBinding
import com.example.qkart.utils.CartManager

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.cartrecyclerview.layoutManager =
            LinearLayoutManager(requireContext())

        binding.cartrecyclerview.adapter =
            CartAddaptar(CartManager.getItems())

        binding.proceedbutton.setOnClickListener {
            startActivity(Intent(requireContext(), PayOutActivity::class.java))
        }

        return binding.root
    }
}
