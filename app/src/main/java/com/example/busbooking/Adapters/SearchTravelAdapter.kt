package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.ClickListener.BookingClickListener
import com.example.busbooking.Models.RouteModel
import com.example.busbooking.ViewModels.SearchViewModel
import com.example.busbooking.databinding.SearchItemBinding

class SearchTravelAdapter(private val travels : List<RouteModel> , private val listener : BookingClickListener , private val viewModel : SearchViewModel):RecyclerView.Adapter<SearchTravelAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (travels.isNullOrEmpty()){
            return 0
        }else{
            return travels.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travels[position])
    }

    inner class ViewHolder(private val binding : SearchItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(travel: RouteModel) {
            binding.departureTime.text = travel.departureTime
            binding.travelTime.text = travel.travelTime
            binding.price.text = "${travel.price} EGP"
            binding.startText.text = travel.start
            binding.endText.text = travel.end
            binding.btnBook.setOnClickListener {
                listener.onClick(travel)

        }
            binding.availableSeat.text = travel.availableSeats.toString()

        }

    }

}