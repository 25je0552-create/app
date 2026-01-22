package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qkart.databinding.MenuItemBinding
import com.example.qkart.model.CartItem
import com.example.qkart.model.FoodModel
import com.example.qkart.utils.CartManager

class MenuAdapter(private val list: MutableList<FoodModel>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(val binding: MenuItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        return MenuViewHolder(
            MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = list[position]

        holder.binding.menuFoodname.text = item.foodName
        holder.binding.menuprice.text = "Rs. ${item.foodPrice}"

        Glide.with(holder.itemView)
            .load(item.imageUrl)
            .into(holder.binding.menuimage)

        holder.binding.menuaddtocart.setOnClickListener {
            CartManager.addItem(
                CartItem(
                    item.foodId,
                    item.foodName,
                    item.foodPrice,
                    item.imageUrl
                )
            )
            Toast.makeText(holder.itemView.context, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = list.size
}
