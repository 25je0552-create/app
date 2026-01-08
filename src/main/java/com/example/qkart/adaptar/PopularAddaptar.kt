package com.example.qkart.adaptar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qkart.databinding.PopularItemBinding

class PopularAddaptar (private val Items:List<String>, private val price:List<String>, private val image:List<Int>) : RecyclerView.Adapter<PopularAddaptar.PopularViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(
        holder: PopularViewHolder,
        position: Int
    ) {
        val item = Items[position]
        val images = image[position]
        val price = price[position]
        holder.bind(item,price,images)
    }

    override fun getItemCount(): Int {
        return Items.size
    }

    class PopularViewHolder (private val binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root) {
        private val images = binding.imageView6
        fun bind(item:String, price: String, image:Int){

            binding.Foodnamepopular.text = item
            binding.pricepopular.text = price
            images.setImageResource(image)

        }

    }
}

