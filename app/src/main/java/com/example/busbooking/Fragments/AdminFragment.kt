package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.busbooking.Adapters.TypesAdapter
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AdminViewModel
import com.example.busbooking.databinding.FragmentAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : Fragment() {
    lateinit var binding: FragmentAdminBinding
    val viewModel : AdminViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getTablesTypes()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAdminBinding.bind(view)
        //observer types
        viewModel.types.observe(viewLifecycleOwner){types->
            val adapter = TypesAdapter(types)
            binding.typesRecycler.adapter = adapter
        }
    }
}