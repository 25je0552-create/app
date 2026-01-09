package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.R
import com.example.qkart.model.AdminFoodModel

class AllItemAdapter(
    private val itemList: MutableList<AdminFoodModel>
) : RecyclerView.Adapter<AllItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView =
            itemView.findViewById(R.id.foodImageViewitemitem)
        val foodName: TextView =
            itemView.findViewById(R.id.AdminFoodName)
        val price: TextView =
            itemView.findViewById(R.id.textView45)
        val quantity: TextView =
            itemView.findViewById(R.id.textView46)

        val minusBtn: ImageButton =
            itemView.findViewById(R.id.imageButton)
        val plusBtn: ImageButton =
            itemView.findViewById(R.id.imageButton2)
        val deleteBtn: ImageButton =
            itemView.findViewById(R.id.imageButton3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]

        holder.foodImage.setImageResource(item.imageRes)
        holder.foodName.text = item.name
        holder.price.text = item.price
        holder.quantity.text = item.quantity.toString()


        holder.plusBtn.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
        }


        holder.minusBtn.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                notifyItemChanged(position)
            }
        }


        holder.deleteBtn.setOnClickListener {
            itemList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemList.size)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
