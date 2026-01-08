package com.example.qkart.adaptar

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.CartItemBinding

class CartAddaptar(private val cartItem : MutableList<String>,private val cartItemprice:MutableList<String>,private val cartImage : MutableList<Int>  ): RecyclerView.Adapter<CartAddaptar.CartViewHolder>() {
private val itemQuantities= IntArray(cartItem.size){1}
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from (parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = cartItem.size
    inner class CartViewHolder(private val binding : CartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply{
                val quantity = itemQuantities[position]
                cartfoodname.text = cartItem[position]
                cartprice.text = cartItemprice[position]
                cartimage.setImageResource(cartImage[position])
                cartitemquantity.text = quantity.toString()
                minusbutton.setOnClickListener {
                    decreaseQuantity(position)
                 }
                plusbutton.setOnClickListener {
                    increaseQuantity(position)
                }
                trashbutton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        deleteitem(itemPosition)
                    }
                }

            }
        }
        private fun increaseQuantity(position: Int){
            if (itemQuantities[position]<10){
                itemQuantities[position]++
                binding.cartitemquantity.text = itemQuantities[position].toString()
            }
        }
        private fun decreaseQuantity(position: Int){
            if (itemQuantities[position]>1){
                itemQuantities[position]--
                binding.cartitemquantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteitem(position : Int){
            cartItem.removeAt(position)
            cartImage.removeAt(position)
            cartItemprice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartItem.size)
        }
    }
}