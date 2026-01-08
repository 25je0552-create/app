package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.NotificationItemBinding

class NotificationAdapter(
    private val notifications: ArrayList<String>,
    private val notificationImages: ArrayList<Int>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {

        val binding = NotificationItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(
            notifications[position],
            notificationImages[position]
        )
    }

    override fun getItemCount(): Int = notifications.size

    inner class NotificationViewHolder(
        private val binding: NotificationItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String, image: Int) {
            binding.NotificationTextView.text = text
            binding.NotificationImageView.setImageResource(image)
        }
    }
}

