package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.busbooking.Adapters.AdminRoutesAdapter
import com.example.busbooking.ClickListener.AdminClickListeners
import com.example.busbooking.Enums.Tables
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AdminRoutesViewModel
import com.example.busbooking.databinding.FragmentAdminRoutesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminRoutesFragment : Fragment() {
    lateinit var binding: FragmentAdminRoutesBinding
    val viewModel: AdminRoutesViewModel by viewModels()
    lateinit var listener: AdminClickListeners
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllRoutes()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAdminRoutesBinding.bind(view)

        //observe routes
        viewModel.routes.observe(viewLifecycleOwner){routes->
            val adapter = AdminRoutesAdapter(routes)
            binding.routesRecycler.adapter = adapter
        }

        //btn add listener
        binding.btnAdd.setOnClickListener {
            listener.onAddButtonClick(Tables.Routes.name)
        }
    }
}