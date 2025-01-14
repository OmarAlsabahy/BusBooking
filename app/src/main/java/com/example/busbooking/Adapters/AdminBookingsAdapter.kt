package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.Models.BookingsModel
import com.example.busbooking.databinding.AdminBookingItemBinding

class AdminBookingsAdapter(private val booking:List<BookingsModel>):RecyclerView.Adapter<AdminBookingsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminBookingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (booking.isNullOrEmpty()){
            return 0
        }else{
            return booking.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(booking[position])
    }

    inner class ViewHolder(private val binding:AdminBookingItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(book: BookingsModel) {
            binding.departureTime.text = book.time
            binding.travelTime.text = book.travelTime
            binding.price.text = book.totalPrice.toString()
            binding.date.text= book.date
            binding.startText.text = book.startLocation
            binding.endText.text = book.endLocation
        }

    }
}