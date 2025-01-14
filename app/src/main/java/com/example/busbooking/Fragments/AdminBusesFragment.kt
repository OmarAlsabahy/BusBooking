package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.busbooking.Adapters.AdminBusesAdapter
import com.example.busbooking.ClickListener.AdminClickListeners
import com.example.busbooking.Enums.Tables
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AdminBusesViewModel
import com.example.busbooking.databinding.FragmentAdminBusesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminBusesFragment : Fragment() {
    lateinit var binding:FragmentAdminBusesBinding
    val viewModel : AdminBusesViewModel by viewModels()
    lateinit var listeners: AdminClickListeners
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllBuses()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_buses, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAdminBusesBinding.bind(view)
        //observe buses
        viewModel.buses.observe(viewLifecycleOwner){buses->
            val adapter = AdminBusesAdapter(buses)
            binding.busesRecycler.adapter = adapter
        }

        binding.btnAdd.setOnClickListener {
            listeners.onAddButtonClick(Tables.Buses.name)
        }
    }


}