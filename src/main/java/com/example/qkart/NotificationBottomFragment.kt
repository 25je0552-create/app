package com.example.qkart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qkart.adaptar.NotificationAdapter
import com.example.qkart.databinding.FragmentNotificationbottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNotificationbottomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationbottomBinding.inflate(inflater, container, false)

        val notification = arrayListOf(
            "Your order has been canceled successfully",
            "Order has been taken by the driver",
            "Congrats! Your order is placed"
        )

        val notificationImage = arrayListOf(
            R.drawable.sademoji,
            R.drawable.truck,
            R.drawable.congrats
        )

        val adapter = NotificationAdapter(notification, notificationImage)

        binding.notificationRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        binding.notificationRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
