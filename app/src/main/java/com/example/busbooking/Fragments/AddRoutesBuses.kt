package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Adapters.AddRoutesBusesAdapter
import com.example.busbooking.ClickListener.AddRoutesBusesClickListener
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AddRoutesBusesViewModel
import com.example.busbooking.databinding.FragmentAddRoutesBusesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoutesBuses : Fragment(), AddRoutesBusesClickListener {
    lateinit var binding: FragmentAddRoutesBusesBinding
    val viewModel : AddRoutesBusesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllBuses()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_routes_buses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddRoutesBusesBinding.bind(view)
        //observe buses
        viewModel.buses.observe(viewLifecycleOwner){buses->
            val adapter = AddRoutesBusesAdapter(buses,this)
            binding.busesRecycler.adapter = adapter
        }
    }

    override fun onclick(busId: String) {
        setFragmentResult("busId",Bundle().apply {
            putString("busId",busId)
        })
        findNavController().popBackStack()
    }
}