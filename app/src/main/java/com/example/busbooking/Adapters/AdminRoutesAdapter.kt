package com.example.busbooking.Adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.databinding.AdminRoutesItemBinding

class AdminRoutesAdapter(private val routes: List<RouteModel>):RecyclerView.Adapter<AdminRoutesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminRoutesItemBinding.inflate(android.view.LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(routes.isNullOrEmpty()){
            return 0
        }else{
            return routes.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(routes[position])
    }

    inner class ViewHolder(private val binding : AdminRoutesItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(route: RouteModel) {
            binding.departureTime.text = route.departureTime
            binding.travelTime.text = route.travelTime
            binding.availableSeat.text = route.availableSeats.toString()
            binding.price.text = route.price.toString()
            binding.startText.text = route.start
            binding.endText.text = route.end
            Glide.with(binding.routeImage)
                .load(route.imageUrl)
                .centerCrop()
                .into(binding.routeImage)
        }

    }
}
