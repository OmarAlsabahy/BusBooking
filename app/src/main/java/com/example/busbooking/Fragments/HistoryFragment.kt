package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.busbooking.Adapters.TicketsAdapter
import com.example.busbooking.R
import com.example.busbooking.ViewModels.HistoryViewModel
import com.example.busbooking.databinding.FragmentHistoryBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    lateinit var binding:FragmentHistoryBinding
    val viewModel: HistoryViewModel by viewModels()
    @Inject
    lateinit var auth:FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUserTickets(auth.currentUser?.uid!!)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHistoryBinding.bind(view)
        //observeTickets
        viewModel.ticket.observe(viewLifecycleOwner){tickets->
            if(tickets.isNotEmpty()){
                val adapter = TicketsAdapter(tickets,viewModel)
                binding.ticketsRecycler.adapter = adapter
                binding.txtNotAvailable.visibility = View.GONE
                binding.ticketsRecycler.visibility = View.VISIBLE
            }else{
                binding.txtNotAvailable.visibility = View.VISIBLE
                binding.ticketsRecycler.visibility = View.GONE
            }
        }
    }
}