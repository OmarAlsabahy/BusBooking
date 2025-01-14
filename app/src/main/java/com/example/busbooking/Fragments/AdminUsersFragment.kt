package com.example.busbooking.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.busbooking.Adapters.AdminUsersAdapter
import com.example.busbooking.R
import com.example.busbooking.ViewModels.AdminUsersViewModel
import com.example.busbooking.databinding.FragmentAdminUsersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminUsersFragment : Fragment() {
    lateinit var binding:FragmentAdminUsersBinding
    val viewModel : AdminUsersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllUsers()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAdminUsersBinding.bind(view)
        //observe users
        viewModel.users.observe(viewLifecycleOwner){users->
            val adapter = AdminUsersAdapter(users)
            binding.userRecycler.adapter = adapter
        }
    }
}