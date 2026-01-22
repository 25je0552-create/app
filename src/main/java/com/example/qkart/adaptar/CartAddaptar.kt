package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.qkart.databinding.CartItemBinding
import com.example.qkart.model.CartItem
import com.example.qkart.utils.CartManager

class CartAddaptar(
    private val cartList: MutableList<CartItem>
) : RecyclerView.Adapter<CartAddaptar.CartViewHolder>() {

    inner class CartViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]

        holder.binding.cartfoodname.text = item.foodName
        holder.binding.cartprice.text = "Rs. ${item.foodPrice}"
        holder.binding.cartitemquantity.text = item.quantity.toString()

        // ✅ CLOUDINARY IMAGE LOAD
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.ic_menu_report_image)
            .into(holder.binding.cartimage)

        // ➕ Increase quantity
        holder.binding.plusbutton.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
        }

        // ➖ Decrease quantity
        holder.binding.minusbutton.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
            }
        }

        // 🗑 Remove item
        holder.binding.trashbutton.setOnClickListener {
            CartManager.removeItem(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartList.size)
        }
    }

    override fun getItemCount(): Int = cartList.size
}
