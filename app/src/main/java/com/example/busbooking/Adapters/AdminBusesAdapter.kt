package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.Models.BusModel
import com.example.busbooking.databinding.AdminBusesItemBinding

class AdminBusesAdapter(private val buses : List<BusModel>):RecyclerView.Adapter<AdminBusesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminBusesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (buses.isNullOrEmpty()){
            return 0
        }else{
            return buses.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(buses[position])
    }

    inner class ViewHolder(private val binding:AdminBusesItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bus: BusModel) {
            binding.busName.text = bus.busName
            binding.busNumber.text = bus.busNumber
            binding.capacity.text = bus.capacity.toString()
        }

    }

}