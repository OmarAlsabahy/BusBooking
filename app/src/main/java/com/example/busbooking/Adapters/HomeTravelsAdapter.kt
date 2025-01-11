package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.databinding.TravelItemBinding

class HomeTravelsAdapter(private val routes:List<RouteModel>):RecyclerView.Adapter<HomeTravelsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TravelItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (!routes.isNullOrEmpty()){
            return routes.size
        }else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(routes[position])
    }


    inner class ViewHolder(private val binding:TravelItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(route: RouteModel) {
            binding.travelName.text = route.end
            binding.travelFare.text = route.price.toString()
            Glide.with(binding.root)
                .load(route.imageUrl)
                .centerCrop()
                .into(binding.travelImage)
        }

    }
}