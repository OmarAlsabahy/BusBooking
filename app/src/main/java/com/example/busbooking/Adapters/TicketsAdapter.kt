package com.example.busbooking.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.busbooking.Models.Bookings
import com.example.busbooking.ViewModels.HistoryViewModel
import com.example.busbooking.databinding.TicketItemBinding
class TicketsAdapter(private val tickets:List<Bookings> , private val viewModel: HistoryViewModel):RecyclerView.Adapter<TicketsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TicketItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (tickets.isNullOrEmpty()){
            return 0
        }else{
            return tickets.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tickets[position])
    }


    inner class ViewHolder(private val binding:TicketItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(ticket: Bookings) {
            binding.startText.text = ticket.startLocation
            binding.endText.text = ticket.endLocation
            binding.date.text = ticket.date
            binding.departureTime.text = ticket.time
            binding.travelTime.text = ticket.travelTime
            binding.price.text= ticket.totalPrice.toString()

            viewModel.getBusNumberByBusId(ticket.busId){busNumber->
                binding.busNumber.text = busNumber
            }
        }

    }

}