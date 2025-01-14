package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.busbooking.Adapters.TypesAdapter
import com.example.busbooking.ClickListener.AdminClickListeners
import com.example.busbooking.ClickListener.TypesButtonClickListener
import com.example.busbooking.Enums.Tables
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AdminViewModel
import com.example.busbooking.databinding.FragmentAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFragment : Fragment() , AdminClickListeners , TypesButtonClickListener {
    lateinit var binding: FragmentAdminBinding
    val viewModel : AdminViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getTablesTypes()
        navigateToFragment(Tables.Buses.name)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    private fun navigateToFragment(name: String) {
        when(name){
            Tables.Buses.name->{
                val fragment = AdminBusesFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer , fragment)
                    .commit()
                fragment.listeners = this
            }
            Tables.Routes.name->{
                val fragment = AdminRoutesFragment()
                fragment.listener =this
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer , fragment)
                    .commit()
            }
            Tables.Users.name->{
                val fragment = AdminUsersFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer,fragment)
                    .commit()
            }
            Tables.Bookings.name->{
                val fragment = AdminBookingsFragment()
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer,fragment)
                    .commit()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAdminBinding.bind(view)
        //observer types
        viewModel.types.observe(viewLifecycleOwner){types->
            val adapter = TypesAdapter(types,this)
            binding.typesRecycler.adapter = adapter
        }
    }

    override fun onAddButtonClick(type: String) {
        when(type){
            Tables.Buses.name->findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToAddBusFragment())
            Tables.Routes.name->findNavController().navigate(AdminFragmentDirections.actionAdminFragmentToAddRoutesFragment())
        }
    }

    override fun onClick(type: String) {
        when(type){
            Tables.Buses.name->navigateToFragment(Tables.Buses.name)
            Tables.Routes.name->navigateToFragment(Tables.Routes.name)
            Tables.Users.name->navigateToFragment(Tables.Users.name)
            Tables.Bookings.name->navigateToFragment(Tables.Bookings.name)
        }
    }

}