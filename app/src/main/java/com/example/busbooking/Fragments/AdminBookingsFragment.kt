package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.busbooking.Adapters.AdminBookingsAdapter
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AdminBookingViewModel
import com.example.busbooking.databinding.FragmentAdminBookingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminBookingsFragment : Fragment() {
    lateinit var binding: FragmentAdminBookingsBinding
    val viewModel : AdminBookingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllBookings()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_bookings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAdminBookingsBinding.bind(view)

        //observe bookings
        viewModel.bookings.observe(viewLifecycleOwner){bookings->
            val adapter = AdminBookingsAdapter(bookings)
            binding.bookingsRecycler.adapter = adapter
        }
    }
}