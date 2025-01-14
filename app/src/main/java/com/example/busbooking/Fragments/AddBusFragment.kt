package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Models.BusModel
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AddBusesViewModel
import com.example.busbooking.databinding.FragmentAddBusBinding
import com.example.busbooking.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBusFragment : Fragment() {
    lateinit var binding: FragmentAddBusBinding
    val viewModel : AddBusesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddBusBinding.bind(view)

        //observe status
        viewModel.isAdded.observe(viewLifecycleOwner){isAdded->
            if (isAdded){
               viewModel.sendNotification("Bus Added Successfully" , requireContext())
            }
        }

        //observe notification send
        viewModel.isSent.observe(viewLifecycleOwner){status->
            if (status){
                findNavController().popBackStack()
            }
        }

        //btn save listener
        binding.btnSave.setOnClickListener {
            validate(binding.busName.text.toString() , binding.busNumber.text.toString() , binding.capacity.text.toString())
        }
    }

    private fun validate(busName: String, busNumber: String, capacity: String) {
        if (!busName.isEmpty()||!busNumber.isEmpty()||!capacity.isEmpty()){
            val bus = BusModel(busName = busName.trim() , busNumber = busNumber.trim() , capacity = capacity.trim().toInt())
            viewModel.addBus(bus)
        }else{
            requireContext().showToast(requireContext() , "Please Fill All Fields")
        }
    }
}