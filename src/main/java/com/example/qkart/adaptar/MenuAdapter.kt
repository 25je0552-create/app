package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.MenuItemBinding
import com.example.qkart.model.CartItem
import com.example.qkart.model.FoodModel
import com.example.qkart.utils.CartManager

class MenuAdapter(
    private val menuList: MutableList<FoodModel>
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding =
            MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuList[position])
    }

    override fun getItemCount(): Int = menuList.size

    inner class MenuViewHolder(
        private val binding: MenuItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodModel) {

            binding.menuFoodname.text = item.foodName
            binding.menuprice.text = "Rs. ${item.foodPrice}"

            // IMAGE PART (for later Firebase Storage use)
            // binding.menuimage.setImageResource(item.imageRes)

            binding.menuaddtocart.setOnClickListener {

                val cartItem = CartItem(
                    foodName = item.foodName,
                    foodPrice = item.foodPrice,
                    imageRes = item.imageRes,
                    quantity = 1
                )

                CartManager.addItem(cartItem)

                // ✅ TOAST CONFIRMATION
                Toast.makeText(
                    binding.root.context,
                    "${item.foodName} added to cart",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
