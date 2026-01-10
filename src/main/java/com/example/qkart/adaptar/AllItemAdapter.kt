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

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.foodImageViewitemitem)
        val name: TextView = view.findViewById(R.id.AdminFoodName)
        val price: TextView = view.findViewById(R.id.textView45)
        val quantity: TextView = view.findViewById(R.id.textView46)
        val minus: ImageButton = view.findViewById(R.id.imageButton)
        val plus: ImageButton = view.findViewById(R.id.imageButton2)
        val delete: ImageButton = view.findViewById(R.id.imageButton3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]

        holder.name.text = item.foodName
        holder.price.text = "Rs.${item.foodPrice}"
        holder.quantity.text = item.quantity.toString()

        holder.plus.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
        }

        holder.minus.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                notifyItemChanged(position)
            }
        }

        holder.delete.setOnClickListener {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = itemList.size
}
