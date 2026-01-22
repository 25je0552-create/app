package com.example.qkart.adaptar

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.ItemItemBinding
import com.example.qkart.model.AdminFoodModel
import com.google.firebase.database.FirebaseDatabase

class AllItemAdapter(
    private val foodList: MutableList<AdminFoodModel>
) : RecyclerView.Adapter<AllItemAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(
        val binding: ItemItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodViewHolder {
        val binding = ItemItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FoodViewHolder,
        position: Int
    ) {
        val food = foodList[position]

        // 🔹 SET DATA
        holder.binding.AdminFoodName.text = food.foodName
        holder.binding.textView45.text = "Rs. ${food.foodPrice}"

        // 🗑 DELETE BUTTON (trash icon)
        holder.binding.imageButton3.setOnClickListener {

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete ${food.foodName}?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteItem(food.foodId, position, holder)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    override fun getItemCount(): Int = foodList.size

    // 🔥 DELETE FROM FIREBASE
    private fun deleteItem(
        foodId: String,
        position: Int,
        holder: FoodViewHolder
    ) {
        FirebaseDatabase.getInstance()
            .reference
            .child("foods")
            .child(foodId)
            .removeValue()
            .addOnSuccessListener {
                foodList.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(
                    holder.itemView.context,
                    "Item deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    holder.itemView.context,
                    "Failed to delete item",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
